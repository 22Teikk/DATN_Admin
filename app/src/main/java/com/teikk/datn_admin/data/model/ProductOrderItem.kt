package com.teikk.datn_admin.data.data

import com.teikk.datn_admin.data.model.OrderItem
import com.teikk.datn_admin.data.model.Product

data class ProductOrderItem(
    var product: Product,
    var orderItem: OrderItem,
)
