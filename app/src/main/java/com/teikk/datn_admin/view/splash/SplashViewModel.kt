package com.teikk.datn_admin.view.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teikk.datn_admin.base.SharedPreferenceUtils
import com.teikk.datn_admin.data.datasource.repository.CategoryRepository
import com.teikk.datn_admin.data.datasource.repository.PaymentMethodRepository
import com.teikk.datn_admin.data.datasource.repository.RoleRepository
import com.teikk.datn_admin.utils.ShareConstant.UID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferenceUtils,
    private val roleRepository: RoleRepository,
    private val categoryRepository: CategoryRepository,
    private val paymentMethodRepository: PaymentMethodRepository
) : ViewModel() {
    private val TAG = "SplashViewModel-TAG"
    private val _isFirstTime = MutableLiveData<Boolean>()
    val isFirstTime get() = _isFirstTime

    val uid = sharedPreferences.getStringValue(UID, "")
    init {
        val firstTime = sharedPreferences.getBooleanValue("isFirstTime", true)
        if (firstTime) {
            firstInit()
        } else {
            _isFirstTime.value = false
        }
    }

    private fun firstInit() = viewModelScope.launch(Dispatchers.IO) {
        roleRepository.fetchRoleData()
        categoryRepository.fetchCategoryData()
        paymentMethodRepository.fetchPaymentMethodData()
        _isFirstTime.postValue(true)
    }
}