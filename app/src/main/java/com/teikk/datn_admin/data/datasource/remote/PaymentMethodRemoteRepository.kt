package com.teikk.datn_admin.data.datasource.remote

import com.teikk.datn_admin.data.datasource.service.ApiService
import com.teikk.datn_admin.base.SharedPreferenceUtils
import com.teikk.datn_admin.utils.ShareConstant
import javax.inject.Inject

class PaymentMethodRemoteRepository @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferenceUtils: SharedPreferenceUtils
) {
    suspend fun getAllPaymentMethods() = apiService.getAllPaymentMethods(ShareConstant.getAdminHeaders(sharedPreferenceUtils))
}