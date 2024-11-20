package com.teikk.datn_admin.di

import android.content.Context
import com.teikk.datn_admin.base.NetworkLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetWork(@ApplicationContext context: Context) : NetworkLiveData {
        return NetworkLiveData(context)
    }

}
