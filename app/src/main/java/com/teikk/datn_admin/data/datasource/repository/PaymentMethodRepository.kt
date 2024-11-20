package com.teikk.datn_admin.data.datasource.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teikk.datn_admin.data.datasource.local.PaymentMethodLocalRepository
import com.teikk.datn_admin.data.datasource.remote.PaymentMethodRemoteRepository
import com.teikk.datn_admin.data.model.PaymentMethod
import javax.inject.Inject

class PaymentMethodRepository @Inject constructor(
    private val paymentMethodRemoteRepository: PaymentMethodRemoteRepository,
    private val paymentMethodLocalRepository: PaymentMethodLocalRepository,
){
    private val _paymentMethodsLiveData = MutableLiveData<List<PaymentMethod>>()
    val paymentMethodsLiveData: LiveData<List<PaymentMethod>> get() = _paymentMethodsLiveData
    suspend fun fetchPaymentMethodData()  {
        val response = paymentMethodRemoteRepository.getAllPaymentMethods()
        if (response.isSuccessful) {
            val paymentMethods = response.body()!!
            paymentMethodLocalRepository.insertPaymentMethods(paymentMethods)
            _paymentMethodsLiveData.postValue(paymentMethods)
        }
    }
}