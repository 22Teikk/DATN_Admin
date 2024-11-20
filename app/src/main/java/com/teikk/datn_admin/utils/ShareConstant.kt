package com.teikk.datn_admin.utils

import com.teikk.datn_admin.base.SharedPreferenceUtils

object ShareConstant {
    private const val TOKEN = "TOKEN"
    const val UID = "UID"

    fun saveToken(sharedPreferenceUtils: SharedPreferenceUtils, token: String) {
        sharedPreferenceUtils.putStringValue(TOKEN, token)
    }

    fun removeToken(sharedPreferenceUtils: SharedPreferenceUtils) =
        sharedPreferenceUtils.putStringValue(
            TOKEN, ""
        )

    fun getAdminHeaders(sharedPreferenceUtils: SharedPreferenceUtils) = mapOf(
        "Authorization" to "Bearer ${sharedPreferenceUtils.getStringValue(TOKEN, "")}",
        "Authentication-Admin" to "teikk"
    )

    fun getHeaders(sharedPreferenceUtils: SharedPreferenceUtils) = mapOf(
        "Authorization" to "Bearer ${sharedPreferenceUtils.getStringValue(TOKEN, "")}"
    )
}