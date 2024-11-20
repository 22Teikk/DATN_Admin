package com.teikk.datn_admin.data.service.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.teikk.datn_admin.data.model.PaymentMethod

@Dao
interface PaymentMethodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaymentMethods(roles: List<PaymentMethod>)

    @Delete
    fun delete(role: PaymentMethod)

    @Query("SELECT * FROM role_tables")
    fun getAllPaymentMethod(): List<PaymentMethod>
}