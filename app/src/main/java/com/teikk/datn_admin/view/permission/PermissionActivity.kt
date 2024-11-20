package com.teikk.datn_admin.view.permission

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseActivity
import com.teikk.datn_admin.databinding.ActivityPermissionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionActivity : BaseActivity<ActivityPermissionBinding>() {
    private lateinit var navController: NavController
    override fun getLayoutResId(): Int {
        return R.layout.activity_permission
    }
    override fun init() {
        navController = findNavController(R.id.permissionNavController)
    }

    companion object {
        const val TAG = "PermissionActivity-TAG"
    }
}