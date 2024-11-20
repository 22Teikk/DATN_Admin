package com.teikk.datn_admin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "category_tables")
data class Category(
    @PrimaryKey
    @SerializedName("_id")
    var id: String,
    var name: String,
    @SerializedName("image_url")
    var imageUrl: String
)
