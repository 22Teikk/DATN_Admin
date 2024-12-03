package com.teikk.datn_admin.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent

object ServiceManager {

    fun startOrRestartService(context: Context, serviceClass: Class<*>) {
        val serviceIntent = Intent(context, serviceClass)
        if (!isServiceRunning(context, serviceClass)) {
            context.startService(serviceIntent)
        }
    }

    fun stopService(context: Context, serviceClass: Class<*>) {
        val serviceIntent = Intent(context, serviceClass)
        context.stopService(serviceIntent)
    }

    private fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}