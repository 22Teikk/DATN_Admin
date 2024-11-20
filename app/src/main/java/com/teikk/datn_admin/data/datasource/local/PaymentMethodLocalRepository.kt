package com.teikk.datn_admin.data.datasource.local

import com.teikk.datn_admin.data.model.PaymentMethod
import com.teikk.datn_admin.data.service.dao.PaymentMethodDao
import javax.inject.Inject

class PaymentMethodLocalRepository @Inject constructor(
    private val paymentMethodDao: PaymentMethodDao
) {
    suspend fun getAllPaymentMethods() = paymentMethodDao.getAllPaymentMethod()
    suspend fun insertPaymentMethods(paymentMethods: List<PaymentMethod>) = paymentMethodDao.insertPaymentMethods(paymentMethods)
    suspend fun deletePaymentMethod(paymentMethod: PaymentMethod) = paymentMethodDao.delete(paymentMethod)
}