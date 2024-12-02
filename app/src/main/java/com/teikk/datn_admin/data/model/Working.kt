package com.teikk.datn_admin.data.model

import com.google.gson.annotations.SerializedName
import com.teikk.datn_admin.utils.DateTimeConstant

data class Working(
    @SerializedName("_id")
    val id: String = "",
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("order_id")
    val orderId: String,
    val type: String,
    val date: String = DateTimeConstant.getCurrentDateStr()
)
