package com.teikk.datn_admin.view.dashboard.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.teikk.datn_admin.view.dashboard.fragment.DeliveryFragment
import com.teikk.datn_admin.view.dashboard.fragment.PendingFragment

class OrderViewPagerAdapter(
    context: AppCompatActivity
) : FragmentStateAdapter(context) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PendingFragment()
            1 -> DeliveryFragment()
            else -> PendingFragment()
        }
    }
}

