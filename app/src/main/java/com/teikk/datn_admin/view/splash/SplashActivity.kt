package com.teikk.datn_admin.view.splash

import android.content.Intent
import android.os.Looper
import androidx.activity.viewModels
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseActivity
import com.teikk.datn_admin.databinding.ActivitySplashBinding
import com.teikk.datn_admin.view.authentication.AuthenticationActivity
import com.teikk.datn_admin.view.dashboard.DashBoardActivity
import com.teikk.datn_admin.view.permission.PermissionActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private val viewModel: SplashViewModel by viewModels()
    override fun getLayoutResId(): Int {
        return R.layout.activity_splash
    }

    override fun initObserve() {
        viewModel.isFirstTime.observe(this) {
            if (it) {
                val intent = Intent(this, PermissionActivity::class.java ).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
            } else {
                val intent = Intent(this, if (viewModel.uid == "") AuthenticationActivity::class.java else DashBoardActivity::class.java ).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(intent)
                }, 2000)
            }
        }
    }

    companion object {
        private val TAG = "SplashActivity-TAG"
    }
}