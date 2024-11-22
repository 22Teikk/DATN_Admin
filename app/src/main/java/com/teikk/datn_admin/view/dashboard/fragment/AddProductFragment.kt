package com.teikk.datn_admin.view.dashboard.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseFragment
import com.teikk.datn_admin.base.GridSpacingItemDecoration
import com.teikk.datn_admin.base.setSafeOnClickListener
import com.teikk.datn_admin.data.model.Image
import com.teikk.datn_admin.data.model.Product
import com.teikk.datn_admin.databinding.FragmentAddProductBinding
import com.teikk.datn_admin.utils.uriToFile
import com.teikk.datn_admin.view.dashboard.DashBoardViewModel
import com.teikk.datn_admin.view.dashboard.adapter.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : BaseFragment<FragmentAddProductBinding>() {
    private val viewModel by activityViewModels<DashBoardViewModel>()
    private val navArgs by navArgs<AddProductFragmentArgs>()
    private val productId = System.currentTimeMillis().toString()
    private val listImage = mutableListOf(Image("","","",""))
    private val listImageLiveData = MutableLiveData(listImage)
    private var imageUrlUpdate: Int ?= null
    private val adapter by lazy {
        ImageAdapter()
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_add_product
    }

    override fun init() {
        with(binding) {
            rcvImage.setHasFixedSize(true)
            rcvImage.layoutManager = GridLayoutManager(requireContext(), 3)
            rcvImage.addItemDecoration(GridSpacingItemDecoration(3, 15, false))
            rcvImage.adapter = adapter
        }
    }

    override fun initEvent() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnDone.setSafeOnClickListener {
                val product = Product(
                    id = productId,
                    name = edtName.text.toString(),
                    description = edtDescription.text.toString(),
                    price = edtPrice.text.toString().toDoubleOrNull()?: 0.0,
                    categoryId = navArgs.categoryId,
                    isSold = true,
                    quantitySold = edtQuantity.text.toString().toIntOrNull() ?: 0,
                    totalTime = edtTotalTime.text.toString().toIntOrNull() ?: 0,
                    thumbnail = "",
                )
                if (listImage.isNotEmpty()) {
                    viewModel.uploadMultipleImage(productId, listImage) {
                        Log.d("kl;sdfjkljj", it)
                        product.thumbnail = it
                        viewModel.createProduct(product) {
                            findNavController().navigateUp()
                        }
                    }
                }
                else {
                    viewModel.createProduct(product) {
                        findNavController().navigateUp()
                    }
                }
            }
        }
        adapter.listener = { item, position ->
            if (item.url.isEmpty()) {
                val intent = Intent().apply {
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    action = Intent.ACTION_GET_CONTENT
                }
                arlSelectMultiple.launch(Intent.createChooser(intent, "Select Picture"))
            } else {
                val intent = Intent().apply {
                    type = "image/*"
                    action = Intent.ACTION_GET_CONTENT
                }
                imageUrlUpdate = position
                arlSelectSingle.launch(intent)
            }
        }
        adapter.removeListener = { item, position ->
            listImage.remove(item)
            listImageLiveData.value = listImage
            adapter.notifyItemRemoved(position)
        }
    }

    private val arlSelectSingle =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data = result.data
                if (data != null) {
                    listImage[imageUrlUpdate!!].url = data.data.toString()
                    listImageLiveData.value = listImage
                    adapter.notifyItemChanged(imageUrlUpdate!!)
                }
            }
        }

    private val arlSelectMultiple = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val data = result.data!!
            if (data.clipData != null) {
                val clipData = data.clipData!!
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    val image = Image("", requireContext().uriToFile(uri)!!.path, productId, "")
                    listImage.add(image)
                }
            } else if (data?.data != null) { // Chỉ chọn 1 hình ảnh
                val uri = data.data!!
                val image = Image("", requireContext().uriToFile(uri)!!.path, productId, "")
                listImage.add(image)
            }
            listImageLiveData.value = listImage
            adapter.notifyDataSetChanged()
        }
    }

    override fun initObserver() {
        listImageLiveData.observe(viewLifecycleOwner) {
            Log.d("sjkhsajklfahsdf", it.toString())
            adapter.submitList(it)
        }
    }

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
}