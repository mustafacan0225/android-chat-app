package com.mustafacan.data.socketio.di

import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import com.mustafacan.core.domain.service.SocketService
import com.mustafacan.data.socketio.factory.SocketFactory
import com.mustafacan.data.socketio.service.SocketServiceImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {

    @Provides
    @Singleton
    fun provideSocketFactory(userLocalRepository: UserLocalRepository): SocketFactory = SocketFactory(userLocalRepository)

    @Provides
    @Singleton
    fun provideSocketService(socketFactory: SocketFactory, moshi: Moshi): SocketService {
        return SocketServiceImpl(socketFactory, moshi)
    }

}