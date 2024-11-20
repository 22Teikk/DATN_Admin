package com.teikk.datn_admin.view.authentication.fragment

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teikk.datn_admin.R
import com.teikk.datn_admin.base.BaseFragment
import com.teikk.datn_admin.databinding.FragmentRegisterBinding
import com.teikk.datn_admin.view.authentication.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel: AuthenticationViewModel by activityViewModels()
    override fun getLayoutResId(): Int {
        return R.layout.fragment_register
    }

    override fun init() {

    }

    override fun initEvent() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnRegister.setOnClickListener {
                val userName = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                viewModel.register(email = userName, password = password) {
                    if (it) {
                        val direction = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(userName, password)
                        findNavController().navigate(direction)
                    } else {
                        Toast.makeText(requireContext(), "Register failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            btnLogin.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override val onBackPressedCallback: OnBackPressedCallback
        get() = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
}