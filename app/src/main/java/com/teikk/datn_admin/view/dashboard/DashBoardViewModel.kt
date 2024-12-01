package com.teikk.datn_admin.view.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teikk.datn_admin.base.SharedPreferenceUtils
import com.teikk.datn_admin.data.datasource.repository.OrderItemRepository
import com.teikk.datn_admin.data.datasource.repository.OrderRepository
import com.teikk.datn_admin.data.datasource.repository.ProductRepository
import com.teikk.datn_admin.data.datasource.repository.UserProfileRepository
import com.teikk.datn_admin.data.model.Order
import com.teikk.datn_admin.data.model.OrderItem
import com.teikk.datn_admin.data.model.UserProfile
import com.teikk.datn_admin.data.service.socket.SocketManager
import com.teikk.datn_admin.utils.NotificationHelper
import com.teikk.datn_admin.utils.ShareConstant.UID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val orderItemRepository: OrderItemRepository,
    private val orderRepository: OrderRepository,
    private val userProfileRepository: UserProfileRepository,
    private val socketManager: SocketManager,
    private val sharedPreferenceUtils: SharedPreferenceUtils,
    private val notificationHelper: NotificationHelper

) : ViewModel() {
    val products = productRepository.products
    private val _orderPending = MutableStateFlow<List<Order>>(emptyList())
    val orderPending get()= _orderPending
    private val _orderDelivery = MutableStateFlow<List<Order>>(emptyList())
    val orderDelivery get()= _orderDelivery
    private val _orderItem = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItem get()= _orderItem
    private val _user = MutableStateFlow<UserProfile>(UserProfile())
    val user get()= _user
    val uid: String by lazy {
        sharedPreferenceUtils.getStringValue(UID, "")
    }

    init {
        fetchProductData()
        initSocket()
    }

    fun initSocket() {
        socketManager.socketConnect()
        socketManager.joinEmployee(uid)
        socketManager.onNewOrder {
            Log.d("ajskdfhkajdsfa", "Have new Order")
            notificationHelper.showNotification("New Order", "You have new order from customer")
            initData()
        }
    }

    fun initData() {
        fetchOrderPending()
        fetchOrderDelivery()
    }

    private fun fetchProductData() = viewModelScope.launch(Dispatchers.IO) {
        productRepository.fetchProduct()
    }

    fun fetchOrderItemData(orderID: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = orderItemRepository.getAllOrderItem(orderID)
        if (response.isSuccessful) {
            _orderItem.value = response.body()!!
        }
    }
    
    private fun fetchOrderPending() = viewModelScope.launch(Dispatchers.IO) {
        val response = orderRepository.getAllOrdersPending()
        if (response.isSuccessful) {
            Log.d("sdfjakhsdkjf", response.body().toString())
            _orderPending.value = response.body()!!
        }
    }

    private fun fetchOrderDelivery() = viewModelScope.launch(Dispatchers.IO) {
        val response = orderRepository.getAllOrdersDelivery()
        if (response.isSuccessful) {
            _orderDelivery.value = response.body()!!
        }
    }

    fun fetchUserDataByID(uid: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = userProfileRepository.getUserProfile(uid)
        if (response.isSuccessful) {
            Log.d("sdkjfhasdjkf", response.body().toString())
            _user.value = response.body()!!
        }
    }

    fun updateOrder(order: Order) = viewModelScope.launch(Dispatchers.IO) {
        val response = orderRepository.updateOrder(order)
        if (response.isSuccessful) {
            if (order.status == "Delivery") {
                val updatedPendingList = _orderPending.value.toMutableList()
                updatedPendingList.remove(order) // Xóa order khỏi pending
                _orderPending.value = updatedPendingList

                // Thêm item vào danh sách delivery
                val updatedDeliveryList = _orderDelivery.value.toMutableList()
                updatedDeliveryList.add(order) // Thêm vào delivery
                _orderDelivery.value = updatedDeliveryList
            } else {
                val updatedDeliveryList = _orderDelivery.value.toMutableList()
                updatedDeliveryList.remove(order) // Xóa order khỏi pending
                _orderDelivery.value = updatedDeliveryList
            }
            socketManager.sendMessage(order.userId, uid, order.id)
            initData()
        }
    }

    fun logout(callback: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        sharedPreferenceUtils.putStringValue(UID, "")
        userProfileRepository.deleteAllUser()
        withContext(Dispatchers.Main) {
            callback()
        }
    }
}