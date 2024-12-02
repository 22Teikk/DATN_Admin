package com.teikk.datn_admin.data.datasource.repository

import com.teikk.datn_admin.data.datasource.service.ApiService
import com.teikk.datn_admin.data.model.Working
import javax.inject.Inject

class WorkingRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun createWorking(working: Working) = apiService.createWorking(working)
    suspend fun getAllWorking(uid: String) = apiService.getAllWorkingByType(uid, "Working")
    suspend fun getAllDelivered(uid: String) = apiService.getAllWorkingByType(uid, "Delivered")
}