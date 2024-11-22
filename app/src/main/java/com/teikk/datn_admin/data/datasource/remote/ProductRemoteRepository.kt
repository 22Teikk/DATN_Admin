package com.teikk.datn_admin.data.datasource.remote

import com.teikk.datn_admin.data.datasource.service.ApiService
import com.teikk.datn_admin.base.SharedPreferenceUtils
import com.teikk.datn_admin.data.model.Product
import com.teikk.datn_admin.utils.ShareConstant
import javax.inject.Inject

class ProductRemoteRepository @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferenceUtils: SharedPreferenceUtils
) {
    suspend fun getAllProducts() = apiService.getAllProducts(ShareConstant.getAdminHeaders(sharedPreferenceUtils))
    suspend fun getProductById(id: String) = apiService.getProductById(ShareConstant.getAdminHeaders(sharedPreferenceUtils), id)
    suspend fun createProduct(product: Product) = apiService.createProduct(ShareConstant.getAdminHeaders(sharedPreferenceUtils), product)
    suspend fun updateProduct(id: String, product: Product) = apiService.updateProduct(ShareConstant.getAdminHeaders(sharedPreferenceUtils), id, product)
}