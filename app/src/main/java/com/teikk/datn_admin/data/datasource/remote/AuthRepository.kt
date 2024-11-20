package com.teikk.datn_admin.data.datasource.remote

import com.teikk.datn_admin.data.datasource.service.ApiService
import com.teikk.datn_admin.base.SharedPreferenceUtils
import com.teikk.datn_admin.data.model.UserProfile
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferenceUtils: SharedPreferenceUtils
) {
    suspend fun login(userProfile: UserProfile) = apiService.login(userProfile)
    suspend fun register(userProfile: UserProfile) = apiService.register(userProfile)
}