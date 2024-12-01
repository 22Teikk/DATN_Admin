package com.teikk.datn_admin.view.dashboard.fragment

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseFragment
import com.teikk.datn_admin.data.data.ProductOrderItem
import com.teikk.datn_admin.databinding.FragmentOrderDetailBinding
import com.teikk.datn_admin.view.dashboard.DashBoardViewModel
import com.teikk.datn_admin.view.dashboard.adapter.OrderItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailFragment(
) : BaseFragment<FragmentOrderDetailBinding>() {
    private val viewModel by activityViewModels<DashBoardViewModel>()
    private val args by navArgs<OrderDetailFragmentArgs>()
    private val orderItemAdapter by lazy {
        OrderItemAdapter()
    }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_order_detail
    }

    override fun init() {
        with(binding) {
            txtType.text = if (args.order.isShipment) "Delivery" else "Pick Up"
            edtDescription.setText(args.order.description)
        }
    }

    override fun initEvent() {
        with(binding) {
            rcvCart.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                adapter = orderItemAdapter
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btncall.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL).apply {
                    data = Uri.parse("tel:${txtCustomerPhone}")
                }
                if (intent.resolveActivity(requireContext().packageManager) != null) {
                    requireContext().startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "No dialer app available", Toast.LENGTH_SHORT).show()
                }
            }
            btnSubmit.setOnClickListener {
                if (args.order.status == "Pending") {
                    viewModel.updateOrder(args.order.copy(status = "Delivery"))
                    findNavController().navigateUp()
                } else {
                    viewModel.updateOrder(args.order.copy(status = "Delivered"))
                    findNavController().navigateUp()
                    // If you want to update map
                }
            }
        }
    }

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            combine(viewModel.products, viewModel.orderItem) { products, orderItem ->
                products.filter { product ->
                    orderItem.any {it.productId == product.id}
                }.map { product ->
                    val orderItem = orderItem.find { it.productId == product.id }
                    ProductOrderItem(
                        product = product,
                        orderItem = orderItem!!
                    )
                }
            }.collectLatest {
                orderItemAdapter.submitList(it.toList())
                val subTotal = it.sumOf {
                    it.orderItem.price
                }
                binding.txtTotal.text = (subTotal).toString() + "$"
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.user.collectLatest {
                Log.d("sdkjfhasjkdf", it.toString())
                with(binding) {
                    txtCustomerName.text = it.name
                    txtCustomerAddress.text = it.address
                    txtCustomerPhone.text = it.phone
                }
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