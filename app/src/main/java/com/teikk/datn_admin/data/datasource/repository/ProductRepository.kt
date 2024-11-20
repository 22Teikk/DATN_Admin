package com.teikk.datn_admin.data.datasource.repository

import com.teikk.datn_admin.data.datasource.local.ProductLocalRepository
import com.teikk.datn_admin.data.datasource.remote.ProductRemoteRepository
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productRemoteRepository: ProductRemoteRepository,
    private val productLocalRepository: ProductLocalRepository,
) {
}