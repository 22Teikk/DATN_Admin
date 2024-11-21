package com.teikk.datn_admin.view.dashboard

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseActivity
import com.teikk.datn_admin.databinding.ActivityDashBoardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardActivity : BaseActivity<ActivityDashBoardBinding>() {
    private lateinit var navController: NavController
    private val viewModel by viewModels<DashBoardViewModel>()
    override fun getLayoutResId(): Int {
        return R.layout.activity_dash_board
    }

    override fun init() {
        navController = findNavController(R.id.dashboardNavController)
        with(binding) {
            bottomNavigationView.setupWithNavController(navController)
            bottomNavigationView.menu.getItem(2).isEnabled = false
        }
    }


    companion object {
        const val TAG = "DashBoardActivityTag"
    }
}