package com.teikk.datn_admin.view.dashboard.fragment

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseFragment
import com.teikk.datn_admin.base.GridSpacingItemDecoration
import com.teikk.datn_admin.base.setSafeOnClickListener
import com.teikk.datn_admin.databinding.FragmentCategoryBinding
import com.teikk.datn_admin.view.dashboard.DashBoardViewModel
import com.teikk.datn_admin.view.dashboard.adapter.CategoryAdapter
import com.teikk.datn_admin.view.dashboard.bottomsheet.AddCategoryBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {
    private val viewModel by activityViewModels<DashBoardViewModel>()
    private val adapter by lazy {
        CategoryAdapter()
    }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_category
    }

    override fun init() {
        with(binding) {
            rcvCategory.adapter = adapter
            rcvCategory.setHasFixedSize(true)
            rcvCategory.layoutManager = androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
            rcvCategory.addItemDecoration(GridSpacingItemDecoration(2, 15, includeEdge = false))
        }
    }

    override fun initEvent() {
        with(binding) {
            btnAdd.setSafeOnClickListener {
                val bottomSheet = AddCategoryBottomSheet()
                bottomSheet.show(parentFragmentManager, bottomSheet.tag)
            }
        }
        adapter.listener = { item, position ->
            val directions = CategoryFragmentDirections.actionCategoryFragmentToProductFragment(item.id)
            findNavController().navigate(directions)
        }
    }

    override fun initObserver() {
        viewModel.category.observe(viewLifecycleOwner) { categories ->
            adapter.submitList(categories)
        }
    }

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {

            }
        }
}