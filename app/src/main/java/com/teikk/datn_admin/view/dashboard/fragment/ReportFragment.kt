package com.teikk.datn_admin.view.dashboard.fragment

import android.app.DatePickerDialog
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseFragment
import com.teikk.datn_admin.databinding.FragmentReportBinding
import com.teikk.datn_admin.view.dashboard.DashBoardViewModel
import com.teikk.datn_admin.view.dashboard.adapter.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>() {
    private val viewModel by activityViewModels<DashBoardViewModel>()
    private val orderAdapter by lazy {
        OrderAdapter()
    }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_report
    }

    override fun init() {
        with(binding) {
            rcvReport.setHasFixedSize(true)
            rcvReport.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
            rcvReport.addItemDecoration(
                androidx.recyclerview.widget.DividerItemDecoration(
                    requireContext(),
                    androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
                )
            )
            rcvReport.adapter = orderAdapter
        }
    }

    override fun initEvent() {
        with(binding) {
            orderAdapter.listener = { item, position ->
                viewModel.fetchOrderItemData(item.id)
                viewModel.fetchUserDataByID(item.userId)
                val action = ReportFragmentDirections.actionReportFragmentToOrderDetailFragment(item, isReport = true)
                findNavController().navigate(action)
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            swipeRefresh.setOnRefreshListener {
                viewModel.initData()
                swipeRefresh.isRefreshing = false
            }
            btnSelect.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { _, selectedYear, selectedMonth, selectedDay ->
                        when {
                            radDay.isChecked -> {
                                // Nếu chọn theo ngày: yyyy-MM-dd
                                val selectedDate = String.format(
                                    "%04d-%02d-%02d",
                                    selectedYear,
                                    selectedMonth + 1,
                                    selectedDay
                                )
                                Log.d("dfajkhsfjkd", selectedDate)

                                observerData(selectedDate, "day")
                            }

                            radMonth.isChecked -> {
                                // Nếu chọn theo tháng: yyyy-MM
                                val selectedMonthYear = String.format(
                                    "%04d-%02d",
                                    selectedYear,
                                    selectedMonth + 1
                                )
                                observerData(selectedMonthYear, "month")
                            }

                            radYear.isChecked -> {
                                // Nếu chọn theo năm: yyyy
                                val selectedYearStr = selectedYear.toString()
                                observerData(selectedYearStr, "year")
                            }
                        }
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }

        }
    }

    fun observerData(date: String, type: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            // Flow cho danh sách "working"
            // Flow cho danh sách "working"
            val listWorking = viewModel.working
                .onStart { emit(emptyList()) } // Phát ra danh sách rỗng ban đầu
                .map { list ->
                    list.filter { working ->
                        when (type) {
                            "day" -> working.date.contains(date)
                            "month" -> working.date.contains(date.substring(0, 7))
                            "year" -> working.date.contains(date.substring(0, 4))
                            else -> true
                        }
                    }.ifEmpty { emptyList() } // Phát ra danh sách rỗng nếu không có kết quả
                }
                .onEach { filteredList ->
                    Log.d("DEBUG_WORKING", "WORKING FLOW: $filteredList")
                }

            // Flow cho danh sách "workingDelivered"
            val listDelivery = viewModel.workingDelivered
                .onStart { emit(emptyList()) } // Phát ra danh sách rỗng ban đầu
                .map { list ->
                    list.filter { delivered ->
                        when (type) {
                            "day" -> delivered.date.contains(date)
                            "month" -> delivered.date.contains(date.substring(0, 7))
                            "year" -> delivered.date.contains(date.substring(0, 4))
                            else -> true
                        }
                    }.ifEmpty { emptyList() } // Phát ra danh sách rỗng nếu không có kết quả
                }
                .onEach { filteredList ->
                    Log.d("DEBUG_DELIVERY", "DELIVERY FLOW: $filteredList")
                }

            combine(listWorking, listDelivery) { workingList, deliveredList ->
                val combinedList = workingList + deliveredList
                val countWorking = combinedList.count { it.type == "Working" }
                val countDelivery = combinedList.count { it.type == "Delivered" }
                Triple(combinedList, countWorking, countDelivery)
            }.collect { (combinedList, countWorking, countDelivery) ->
                binding.txtCountWorking.text = countWorking.toString()
                binding.txtCountDelivery.text = countDelivery.toString()
                val list = combinedList.map {
                    it.orderId
                }
                viewModel.fetchOrderByWorking(list)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orderWorking.collectLatest {
                val sortedOrders = it.sortedBy { order ->
                    order.createdAt
                }
                orderAdapter.submitList(sortedOrders)
            }
        }

    }

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
}