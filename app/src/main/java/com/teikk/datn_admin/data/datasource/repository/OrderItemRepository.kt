package com.teikk.datn_admin.data.datasource.repository

import com.teikk.datn_admin.data.datasource.service.ApiService
import javax.inject.Inject

class OrderItemRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllOrderItem(uid: String) = apiService.getOrderItemForOrder(uid)
    
}