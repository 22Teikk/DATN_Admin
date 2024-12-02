package com.teikk.datn_admin.di

import com.teikk.datn_admin.data.datasource.local.CategoryLocalRepository
import com.teikk.datn_admin.data.datasource.local.PaymentMethodLocalRepository
import com.teikk.datn_admin.data.datasource.local.ProductLocalRepository
import com.teikk.datn_admin.data.datasource.local.RoleLocalRepository
import com.teikk.datn_admin.data.datasource.local.UserProfileLocalRepository
import com.teikk.datn_admin.data.datasource.remote.CategoryRemoteRepository
import com.teikk.datn_admin.data.datasource.remote.PaymentMethodRemoteRepository
import com.teikk.datn_admin.data.datasource.remote.ProductRemoteRepository
import com.teikk.datn_admin.data.datasource.remote.RoleRemoteRepository
import com.teikk.datn_admin.data.datasource.remote.UserProfileRemoteRepository
import com.teikk.datn_admin.data.datasource.repository.CategoryRepository
import com.teikk.datn_admin.data.datasource.repository.OrderRepository
import com.teikk.datn_admin.data.datasource.repository.PaymentMethodRepository
import com.teikk.datn_admin.data.datasource.repository.ProductRepository
import com.teikk.datn_admin.data.datasource.repository.RoleRepository
import com.teikk.datn_admin.data.datasource.repository.UserProfileRepository
import com.teikk.datn_admin.data.datasource.repository.WorkingRepository
import com.teikk.datn_admin.data.datasource.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRoleRepository(remoteRepository: RoleRemoteRepository, localRepository: RoleLocalRepository) = RoleRepository(remoteRepository, localRepository)

    @Provides
    @Singleton
    fun provideCategoryRepository(remoteRepository: CategoryRemoteRepository, localRepository: CategoryLocalRepository) = CategoryRepository(remoteRepository, localRepository)


    @Provides
    @Singleton
    fun providePaymentMethodRepository(remoteRepository: PaymentMethodRemoteRepository, localRepository: PaymentMethodLocalRepository) = PaymentMethodRepository(remoteRepository, localRepository)

    @Provides
    @Singleton
    fun provideUserProfileRepository(remoteRepository: UserProfileRemoteRepository, localRepository: UserProfileLocalRepository) = UserProfileRepository(remoteRepository, localRepository)

    @Provides
    @Singleton
    fun provideProductRepository(remoteRepository: ProductRemoteRepository, localRepository: ProductLocalRepository) = ProductRepository(remoteRepository, localRepository)

    @Provides
    @Singleton
    fun provideOrderRepository(apiService: ApiService) = OrderRepository(apiService)

    @Provides
    @Singleton
    fun provideWorkingRepository(apiService: ApiService) = WorkingRepository(apiService)
}