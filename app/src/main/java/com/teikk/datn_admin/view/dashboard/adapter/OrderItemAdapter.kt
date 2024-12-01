package com.teikk.datn_admin.view.dashboard.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseListAdapter
import com.teikk.datn_admin.data.data.ProductOrderItem
import com.teikk.datn_admin.databinding.ItemOrderItemBinding

class OrderItemAdapter(
) : BaseListAdapter<ProductOrderItem, ItemOrderItemBinding>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductOrderItem>() {
            override fun areItemsTheSame(oldItem: ProductOrderItem, newItem: ProductOrderItem): Boolean {
                return oldItem.orderItem.id == newItem.orderItem.id
            }

            override fun areContentsTheSame(oldItem: ProductOrderItem, newItem: ProductOrderItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun getLayout(viewType: Int): Int {
        return R.layout.item_order_item
    }

    override fun onBindViewHolder(
        holder: BaseListAdapter.Companion.BaseViewHolder<ItemOrderItemBinding>,
        position: Int
    ) {
        val item = getItem(position)
        val product = item.product
        val orderItem = item.orderItem
        with(holder.binding) {
            Glide.with(root).load(product.thumbnail).into(imgCart)
            txtName.text = product.name
            txtPrice.text = "$ " + product.price.toString()
            txtTotal.text = orderItem.quantity.toString()
            txtCount.text = orderItem.quantity.toString() + " items"
        }
    }

}