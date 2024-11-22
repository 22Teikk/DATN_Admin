package com.teikk.datn_admin.view.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.teikk.datn_admin.data.datasource.repository.CategoryRepository
import com.teikk.datn_admin.data.datasource.repository.ProductRepository
import com.teikk.datn_admin.data.datasource.repository.UploadFileRepository
import com.teikk.datn_admin.data.model.Category
import com.teikk.datn_admin.data.model.Image
import com.teikk.datn_admin.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val uploadFileRepository: UploadFileRepository,
    private val productRepository: ProductRepository,

) : ViewModel() {
    val category = categoryRepository.categoriesLiveData
    val products = productRepository.products
    init {
        fetchCategoryData()
        fetchProductData()
    }

    private fun fetchCategoryData() = viewModelScope.launch(Dispatchers.IO) {
        categoryRepository.fetchCategoryData()
    }

    fun uploadFile(file: File, callback: (String?) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        val result = uploadFileRepository.uploadFile(file)
        withContext(Dispatchers.Main) {
            callback(result)
        }
    }
    
    fun createCategory(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        categoryRepository.createCategory(category)
    }
    
    private fun fetchProductData() = viewModelScope.launch(Dispatchers.IO) {
        productRepository.fetchProduct()
    }

    fun uploadMultipleImage(productId: String, images :List<Image>, callback: (String) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        val files = images.filter {
            it.url.isNotEmpty()
        }.map {
            File(it.url)
        }.toList()
        val response = async {
            uploadFileRepository.uploadImages(productId = productId, files = files, feedbackId = null)
        }.await()
        if (response.isSuccessful) {
            callback(response.body()!![0].url)
        } else {
            callback("")
        }
    }
    
    fun createProduct(product: Product, callback: (Boolean) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        val response = productRepository.createProduct(product)
        withContext(Dispatchers.Main) {
            callback(response.isSuccessful)
        }
    }
}