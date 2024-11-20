package com.teikk.datn_admin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "payment_method_table")
data class PaymentMethod(
    @PrimaryKey
    @SerializedName("_id")
    var id: String,
    var name: String,
)