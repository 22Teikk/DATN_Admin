package com.teikk.datn_admin.view.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.GoogleMap
import com.teikk.datn_admin.base.SharedPreferenceUtils
import com.teikk.datn_admin.data.datasource.repository.OrderRepository
import com.teikk.datn_admin.data.datasource.repository.WorkingRepository
import com.teikk.datn_admin.data.model.Order
import com.teikk.datn_admin.data.model.Working
import com.teikk.datn_admin.data.service.socket.SocketManager
import com.teikk.datn_admin.utils.ShareConstant.UID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val socketManager: SocketManager,
    private val sharedPreferenceUtils: SharedPreferenceUtils,
    private val orderRepository: OrderRepository,
    private val workingRepository: WorkingRepository
) : ViewModel(){
    val uid: String by lazy {
        sharedPreferenceUtils.getStringValue(UID, "")
    }

    private val _mapType = MutableLiveData<Int>(1)
    val mapType: LiveData<Int>
        get() = _mapType

    fun changeTypeMap(mapType: Int) {
        viewModelScope.launch {
            _mapType.value = mapType
        }
    }

    fun updateOrder(order: Order, callback: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        val response = orderRepository.updateOrder(order)
        if (response.isSuccessful) {
            if (order.isShipment) {
                createWorking(Working(userId = uid, orderId = order.id, type = "Delivered"))
            }
            socketManager.sendMessage(order.userId, uid, order.id)
            withContext(Dispatchers.Main) {
                callback()
            }
        }
    }

    private fun createWorking(working: Working) = viewModelScope.launch {
        workingRepository.createWorking(working)
    }

    fun sendLocationToCustomer(customerID: String, orderID: String ,lat: Double, long: Double) = socketManager.sendLocation(customerID, orderID, lat, long)
}