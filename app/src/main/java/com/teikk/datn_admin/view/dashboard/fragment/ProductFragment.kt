package com.teikk.datn_admin.view.dashboard.fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseFragment
import com.teikk.datn_admin.databinding.FragmentProductBinding
import com.teikk.datn_admin.view.dashboard.DashBoardActivity
import com.teikk.datn_admin.view.dashboard.DashBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : BaseFragment<FragmentProductBinding>() {
    private val viewModel by activityViewModels<DashBoardViewModel>()
    private val navArgs: ProductFragmentArgs by navArgs()
    override fun getLayoutResId(): Int {
        return R.layout.fragment_product
    }

    override fun init() {
        (requireActivity() as DashBoardActivity).hideBottomNav()
    }

    override fun initEvent() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnAdd.setOnClickListener {
                val directions = ProductFragmentDirections.actionProductFragmentToAddProductFragment(navArgs.categoryId)
                findNavController().navigate(directions)
            }
        }
    }

    override fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.map {
                it.filter {
                    it.id == navArgs.categoryId
                }
            }.collectLatest { products ->

            }
        }
    }

    override fun onDestroy() {
        (requireActivity() as DashBoardActivity).showBottomNav()
        super.onDestroy()
    }

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
}