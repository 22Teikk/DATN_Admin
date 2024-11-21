package com.teikk.datn_admin.data.datasource.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teikk.datn_admin.data.datasource.local.CategoryLocalRepository
import com.teikk.datn_admin.data.datasource.remote.CategoryRemoteRepository
import com.teikk.datn_admin.data.model.Category
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryRemoteRepository: CategoryRemoteRepository,
    private val categoryLocalRepository: CategoryLocalRepository,
){
    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>> get() = _categoriesLiveData
    suspend fun fetchCategoryData()  {
        val localData = categoryLocalRepository.getAllCategories()
        if (localData.isEmpty()) {
            val response = categoryRemoteRepository.getAllCategories()
            if (response.isSuccessful) {
                val categories = response.body()!!
                categoryLocalRepository.insertCategories(categories)
                _categoriesLiveData.postValue(categories)
            }
        } else {
            _categoriesLiveData.postValue(localData)
        }

    }

    suspend fun createCategory(category: Category) = runBlocking {
        async {
            categoryLocalRepository.insertCategories(listOf(category))
            categoryRemoteRepository.createCategory(category)
        }.await()
        fetchCategoryData()
    }
}