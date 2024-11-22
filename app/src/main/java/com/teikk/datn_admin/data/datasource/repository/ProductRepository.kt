package com.teikk.datn_admin.data.datasource.repository

import com.teikk.datn_admin.data.datasource.local.ProductLocalRepository
import com.teikk.datn_admin.data.datasource.remote.ProductRemoteRepository
import com.teikk.datn_admin.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productRemoteRepository: ProductRemoteRepository,
    private val productLocalRepository: ProductLocalRepository,
) {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products get()= _products
    suspend fun fetchProduct() {
        val response = productRemoteRepository.getAllProducts()
        if (response.isSuccessful) {
            val products = response.body()!!
            productLocalRepository.insertProducts(products)
        }
        productLocalRepository.getProductAsFlow().collect {
            _products.value = it
        }
    }

    suspend fun createProduct(product : Product) = productRemoteRepository.createProduct(product)
}