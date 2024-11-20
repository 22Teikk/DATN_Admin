package com.teikk.datn_admin.di

import com.teikk.datn_admin.data.datasource.local.CategoryLocalRepository
import com.teikk.datn_admin.data.datasource.local.PaymentMethodLocalRepository
import com.teikk.datn_admin.data.datasource.local.ProductLocalRepository
import com.teikk.datn_admin.data.datasource.local.RoleLocalRepository
import com.teikk.datn_admin.data.datasource.local.UserProfileLocalRepository
import com.teikk.datn_admin.data.service.dao.CategoryDao
import com.teikk.datn_admin.data.service.dao.PaymentMethodDao
import com.teikk.datn_admin.data.service.dao.ProductDao
import com.teikk.datn_admin.data.service.dao.RoleDao
import com.teikk.datn_admin.data.service.dao.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    @Singleton
    fun provideRoleRepository(roleDao: RoleDao) = RoleLocalRepository(roleDao)

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDao: CategoryDao) = CategoryLocalRepository(categoryDao)

    @Provides
    @Singleton
    fun providePaymentMethodRepository(paymentMethodDao: PaymentMethodDao) = PaymentMethodLocalRepository(paymentMethodDao)

    @Provides
    @Singleton
    fun provideUserProfileRepository(userProfileDao: UserProfileDao) = UserProfileLocalRepository(userProfileDao)

    @Provides
    @Singleton
    fun provideProductRepository(productDao: ProductDao) = ProductLocalRepository(productDao)
}