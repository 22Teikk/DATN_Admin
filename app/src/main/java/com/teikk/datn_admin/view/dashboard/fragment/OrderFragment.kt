package com.teikk.datn_admin.view.dashboard.fragment

import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseFragment
import com.teikk.datn_admin.databinding.FragmentOrderBinding
import com.teikk.datn_admin.view.authentication.AuthenticationActivity
import com.teikk.datn_admin.view.dashboard.DashBoardActivity
import com.teikk.datn_admin.view.dashboard.DashBoardViewModel
import com.teikk.datn_admin.view.dashboard.adapter.OrderViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding>() {
    private val viewModel by activityViewModels<DashBoardViewModel>()
    override fun getLayoutResId(): Int {
        return R.layout.fragment_order
    }

    override fun init() {
        setUpTabLayoutViewPager()
    }

    private fun setUpTabLayoutViewPager() {
        val adapter = OrderViewPagerAdapter(requireActivity() as AppCompatActivity)
        with (binding) {
            viewPager2.adapter = adapter
            TabLayoutMediator(tablayout, viewPager2) { tab, position ->
                when (position) {
                    0 -> tab.text = "Pending"
                    1 -> tab.text = "Delivery"
                }
            }.attach()
        }
    }

    override fun initEvent() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout() {
                startActivity(Intent(requireActivity(), AuthenticationActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }
        }
        binding.btnReport.setOnClickListener {
            val directions = OrderFragmentDirections.actionOrderFragmentToReportFragment()
            findNavController().navigate(directions)
        }
    }

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {

            }
        }
}