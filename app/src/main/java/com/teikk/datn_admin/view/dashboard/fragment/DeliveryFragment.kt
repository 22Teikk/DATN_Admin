package com.teikk.datn_admin.view.dashboard.fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseFragment
import com.teikk.datn_admin.databinding.FragmentDeliveryBinding
import com.teikk.datn_admin.view.dashboard.DashBoardViewModel
import com.teikk.datn_admin.view.dashboard.adapter.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeliveryFragment : BaseFragment<FragmentDeliveryBinding>() {
    private val viewModel by activityViewModels<DashBoardViewModel>()
    private val orderAdapter by lazy {
        OrderAdapter()
    }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_delivery
    }

    override fun init() {
        with(binding) {
            rcvDelivery.setHasFixedSize(true)
            rcvDelivery.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
            rcvDelivery.addItemDecoration(
                androidx.recyclerview.widget.DividerItemDecoration(
                    requireContext(),
                    androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
                )
            )
            rcvDelivery.adapter = orderAdapter
        }
    }

    override fun initEvent() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.initData()
            binding.refreshLayout.isRefreshing = false
        }
        orderAdapter.listener = { item, position ->
            viewModel.fetchOrderItemData(item.id)
            viewModel.fetchUserDataByID(item.userId)
            val action = OrderFragmentDirections.actionOrderFragmentToOrderDetailFragment(item)
            findNavController().navigate(action)
        }
    }

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.orderDelivery.collectLatest {
                val sortedOrders = it.sortedBy { order ->
                    order.createdAt
                }
                orderAdapter.submitList(sortedOrders)
            }
        }
    }

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {

            }
        }
}