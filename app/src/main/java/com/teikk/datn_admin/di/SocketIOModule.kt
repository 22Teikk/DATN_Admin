package com.teikk.datn_admin.di

import com.teikk.datn_admin.di.RemoteDataModule.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.engineio.client.transports.WebSocket
import java.net.Socket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketIOModule {
    private val TRANSPORTS = arrayOf(WebSocket.NAME)
    @Provides
    @Singleton
    fun provideSocketIO():
            io.socket.client.Socket {
        val option = IO.Options.builder()
            .setTransports(TRANSPORTS)
            .setTimeout(10000L)
            .build()
        return IO.socket(BASE_URL, option)
    }
}
