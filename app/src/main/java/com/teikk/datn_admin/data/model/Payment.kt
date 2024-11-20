package com.teikk.datn_admin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.teikk.datn_admin.utils.DateTimeConstant

@Entity(tableName = "payment_tables")
data class Payment(
    @PrimaryKey
    @SerializedName("_id")
    var id: String,
    var amount: Double,
    @SerializedName("created_at")
    var createdAt: String = DateTimeConstant.getCurrentTimestampStr(),
    @SerializedName("paymentmethod_id")
    var paymentMethodID: String
)
