package com.teikk.datn_admin.view.dashboard.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseListAdapter
import com.teikk.datn_admin.data.model.Category
import com.teikk.datn_admin.databinding.ItemCategoryBinding

class CategoryAdapter : BaseListAdapter<Category, ItemCategoryBinding>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    }
    override fun getLayout(viewType: Int): Int {
        return R.layout.item_category
    }

    override fun onBindViewHolder(
        holder: BaseListAdapter.Companion.BaseViewHolder<ItemCategoryBinding>,
        position: Int
    ) {
        with(holder.binding) {
            val category = getItem(position)
            txtName.text = category.name
            Glide.with(root).load(category.imageUrl).into(imgCategory)
            root.setOnClickListener {
                listener?.invoke(category, position)
            }
        }
    }

}