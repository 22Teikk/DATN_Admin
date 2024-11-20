package com.teikk.datn_admin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("image_tables")
data class Image(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,
    val url: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("feedback_id")
    var feedbackId: String
)
