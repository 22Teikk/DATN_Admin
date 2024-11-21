package com.teikk.datn_admin.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<T : ViewDataBinding>(
) : BottomSheetDialogFragment(){
    lateinit var binding: T
    private var itemClickListener: ((item: Any, position: Int) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // Sử dụng LayoutInflater từ dialog.context để khởi tạo DataBinding
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), null, false)

        // Gán binding.root cho dialog
        bottomSheetDialog.setContentView(binding.root)


        // Gán LifecycleOwner để Binding theo dõi LiveData
        binding.lifecycleOwner = this

        // Khởi tạo View và sự kiện
        initView()
        initEvent()
        return bottomSheetDialog
    }


    protected abstract fun getLayoutResId(): Int
    open fun initView() {}
    open fun initEvent() {}

}