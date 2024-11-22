package com.teikk.datn_admin.view.dashboard.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseListAdapter
import com.teikk.datn_admin.data.model.Image
import com.teikk.datn_admin.databinding.ItemImageBinding

class ImageAdapter : BaseListAdapter<Image, ItemImageBinding>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.url == newItem.url
            }
        }
    }

    var removeListener: ((item: Image, position: Int) -> Unit)? = null
    override fun getLayout(viewType: Int): Int {
        return R.layout.item_image
    }

    override fun onBindViewHolder(
        holder: BaseListAdapter.Companion.BaseViewHolder<ItemImageBinding>,
        position: Int
    ) {
        with(holder.binding) {
            val image = getItem(position)
            if (image.url.isEmpty()) {
                imgImage.setImageResource(R.drawable.icon_none)
                imgRemove.visibility = View.GONE
            } else {
                Log.d("sjkhsajklfahsdf", image.url)
                Glide.with(root).load(image.url).into(imgImage)
            }
            imgRemove.setOnClickListener {
                removeListener?.invoke(image, position)
            }
            root.setOnClickListener {
                listener?.invoke(image, position)
            }
        }
    }


}