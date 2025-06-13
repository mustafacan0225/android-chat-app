package com.mustafacan.data.network.di

import com.mustafacan.core.domain.repository.api.AuthRepository
import com.mustafacan.core.domain.repository.api.UserRepository
import com.mustafacan.data.network.repository.AuthRepositoryImpl
import com.mustafacan.data.network.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository {
        return repositoryImpl
    }

    @Singleton
    @Provides
    fun provideUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository {
        return repositoryImpl
    }

}