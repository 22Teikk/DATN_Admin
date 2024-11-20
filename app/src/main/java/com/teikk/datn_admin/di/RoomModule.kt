package com.teikk.datn_admin.di

import android.content.Context
import com.teikk.datn_admin.data.datasource.service.DatabaseApp
import com.teikk.datn_admin.data.service.dao.CategoryDao
import com.teikk.datn_admin.data.service.dao.PaymentMethodDao
import com.teikk.datn_admin.data.service.dao.RoleDao
import com.teikk.datn_admin.data.service.dao.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) : DatabaseApp {
        return DatabaseApp.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideRoleDao(databaseApp: DatabaseApp) : RoleDao {
        return databaseApp.roleDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(databaseApp: DatabaseApp) : CategoryDao {
        return databaseApp.categoryDao()
    }

    @Singleton
    @Provides
    fun providePaymentMethodDao(databaseApp: DatabaseApp) : PaymentMethodDao {
        return databaseApp.paymentMethodDao()
    }

    @Singleton
    @Provides
    fun provideUserProfileDao(databaseApp: DatabaseApp) : UserProfileDao {
        return databaseApp.userProfileDao()
    }
}