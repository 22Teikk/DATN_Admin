package com.teikk.datn_admin.data.datasource.repository

import com.teikk.datn_admin.data.datasource.service.ApiService
import com.teikk.datn_admin.data.model.Order
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllOrdersPending() = apiService.getAllOrdersByStatus("Pending")
    suspend fun getAllOrdersDelivery() = apiService.getAllOrdersByStatus("Delivery")
    suspend fun updateOrder(order: Order) = apiService.updateOrder(order.id, order)
}