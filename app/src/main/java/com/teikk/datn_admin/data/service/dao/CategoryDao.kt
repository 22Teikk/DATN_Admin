package com.teikk.datn_admin.data.service.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teikk.datn_admin.data.model.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<Category>)

    @Delete
    fun delete(category: Category)

    @Query("SELECT * FROM category_tables")
    fun getAllCategory(): List<Category>

}