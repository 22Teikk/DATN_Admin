package com.teikk.datn_admin.view.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.teikk.datn_admin.data.datasource.repository.CategoryRepository
import com.teikk.datn_admin.data.datasource.repository.UploadFileRepository
import com.teikk.datn_admin.data.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val uploadFileRepository: UploadFileRepository

) : ViewModel() {
    val category = categoryRepository.categoriesLiveData
    init {
        fetchCategoryData()
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
}