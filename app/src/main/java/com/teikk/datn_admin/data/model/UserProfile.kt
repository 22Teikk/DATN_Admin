package com.teikk.datn_admin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.teikk.datn_admin.utils.DateTimeConstant

@Entity(tableName = "user_table")
data class UserProfile(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("_id")
    var id: String = "",
    var address: String = "",
    @SerializedName("created_at")
    var createdAt: String = DateTimeConstant.getCurrentTimestampStr(),
    var description: String = "",
    var email: String = "",
    @SerializedName("image_url")
    var imageUrl: String? = null,
    var lat: Double = 0.0,
    var long: Double = 0.0,
    var name: String = "",
    var password: String = "",
    var phone: String = "",
    @SerializedName("role_id")
    var roleId: String = "1",
    @SerializedName("store_id")
    var storeId: String? = null,
    var username: String = ""
)