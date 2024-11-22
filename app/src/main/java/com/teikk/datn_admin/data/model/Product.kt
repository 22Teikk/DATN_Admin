package com.teikk.datn_admin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("product_tables")
data class Product(
    @PrimaryKey
    @SerializedName("_id")
    var id: String,
    var name: String,
    var description: String,
    var price: Double,
    @SerializedName("quantity_sold")
    var quantitySold: Int,
    @SerializedName("is_sold")
    var isSold: Boolean,
    @SerializedName("total_time")
    var totalTime: Int,
    var thumbnail: String,
    @SerializedName("category_id")
    var categoryId: String,
    @SerializedName("discount_id")
    var discountId: String? = null,
    @SerializedName("feedback_id")
    var feedbackId: String? = null
)
