package com.teikk.datn_admin.view.dashboard.bottomsheet

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseBottomSheet
import com.teikk.datn_admin.data.model.Category
import com.teikk.datn_admin.databinding.BottomSheetAddCategoryBinding
import com.teikk.datn_admin.utils.uriToFile
import com.teikk.datn_admin.view.dashboard.DashBoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCategoryBottomSheet : BaseBottomSheet<BottomSheetAddCategoryBinding>() {
    private val viewModel by activityViewModels<DashBoardViewModel>()
    private var imageUri: Uri? = null
    override fun getLayoutResId(): Int {
        return R.layout.bottom_sheet_add_category
    }

    override fun initEvent() {
        with(binding) {
            btnSelectImage.setOnClickListener {
                val intent = Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                }
                arl.launch(intent)
            }
            btnDone.setOnClickListener {
                val name = edtCategoryName.text.toString()
                if (name.isBlank()) {
                    return@setOnClickListener
                }
                if (imageUri != null) {
                    viewModel.uploadFile(requireContext().uriToFile(imageUri!!)!!) {
                        val category = Category(id = System.currentTimeMillis().toString(), name = name, imageUrl = it!!)
                        viewModel.createCategory(category)
                        dismiss()
                    }
                } else {
                    val category = Category(id = System.currentTimeMillis().toString(), name = name, imageUrl ="")
                    viewModel.createCategory(category)
                    dismiss()
                }
            }
        }
    }

    private val arl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            imageUri = result.data?.data
        }
    }
}