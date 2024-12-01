package com.teikk.datn_admin.view.dashboard.adapter

import androidx.recyclerview.widget.DiffUtil
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseListAdapter
import com.teikk.datn_admin.data.model.Order
import com.teikk.datn_admin.databinding.ItemOrderBinding

class OrderAdapter : BaseListAdapter<Order, ItemOrderBinding>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }

        }
    }
    override fun getLayout(viewType: Int): Int {
        return R.layout.item_order
    }

    override fun onBindViewHolder(
        holder: BaseListAdapter.Companion.BaseViewHolder<ItemOrderBinding>,
        position: Int
    ) {
        holder.binding.apply {
            val item = getItem(position)
            txtCreate.text = item.createdAt
            txtTotal.text = item.total.toString()
            txtType.text = if (item.isShipment) "Delivery" else "Pickup"
            root.setOnClickListener {
                listener?.invoke(item, position)
            }
        }
    }

}