package com.teikk.datn_admin.view.dashboard.fragment

import androidx.activity.OnBackPressedCallback
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseFragment
import com.teikk.datn_admin.databinding.FragmentStatisticalBinding

class StatisticalFragment : BaseFragment<FragmentStatisticalBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_statistical
    }

    override fun init() {
    }

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {

            }
        }
}