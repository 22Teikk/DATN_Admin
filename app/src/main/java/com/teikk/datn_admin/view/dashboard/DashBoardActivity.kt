package com.teikk.datn_admin.view.dashboard

import android.util.TypedValue
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
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

    fun hideBottomNav() {
        with(binding) {
            main.setPadding(0)
            bottomAppBar.visibility = View.GONE
            btnChat.visibility = View.GONE
        }
    }

    fun showBottomNav() {
        with( binding) {
            val tv = TypedValue()
            if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                val actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
                main.setPadding(0, 0, 0, actionBarHeight + 5)
            }
            bottomAppBar.visibility = View.VISIBLE
            btnChat.visibility = View.VISIBLE
        }
    }

    companion object {
        const val TAG = "DashBoardActivityTag"
    }
}