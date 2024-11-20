package com.teikk.datn_admin.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.teikk.datn_admin.utils.DateTimeConstant

@Entity(tableName = "cart_tables")
data class Cart(
    @SerializedName("_id")
    var id: String,
    @SerializedName("user_id")
    var userId: String,
    @SerializedName("product_id")
    var productId: String,
    @SerializedName("created_at")
    var createdAt: String = DateTimeConstant.getCurrentTimestampStr(),
    var quantity: Int = 1,
)