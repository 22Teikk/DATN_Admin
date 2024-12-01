package com.teikk.datn_admin.data.datasource.remote

import com.teikk.datn_admin.data.datasource.service.ApiService
import com.teikk.datn_admin.base.SharedPreferenceUtils
import com.teikk.datn_admin.data.model.UserProfile
import com.teikk.datn_admin.utils.ShareConstant
import javax.inject.Inject

class UserProfileRemoteRepository @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferenceUtils: SharedPreferenceUtils
) {
    suspend fun updateUserProfile(user: UserProfile) = apiService.updateUserProfile(ShareConstant.getAdminHeaders(sharedPreferenceUtils), user.id, user)
    suspend fun getUserProfile(userId: String) = apiService.getAllUserProfilesByID(userId)
}