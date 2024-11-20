package com.teikk.datn_admin.data.datasource.local

import com.teikk.datn_admin.data.model.Category
import com.teikk.datn_admin.data.service.dao.CategoryDao
import javax.inject.Inject

class CategoryLocalRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    suspend fun getAllCategories() = categoryDao.getAllCategory()
    suspend fun insertCategories(categories: List<Category>) = categoryDao.insertCategories(categories)
    suspend fun deleteCategory(category: Category) = categoryDao.delete(category)
}