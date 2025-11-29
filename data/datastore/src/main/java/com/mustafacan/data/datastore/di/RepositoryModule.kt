package com.mustafacan.data.datastore.di

import com.mustafacan.core.domain.repository.datastore.MessagesLocalRepository
import com.mustafacan.core.domain.repository.datastore.UserLocalRepository
import com.mustafacan.data.datastore.repository.MessagesLocalRepositoryImpl
import com.mustafacan.data.datastore.repository.UserLocalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserLocalRepository(
        impl: UserLocalRepositoryImpl
    ): UserLocalRepository

    @Binds
    abstract fun bindMessagesLocalRepository(
        impl: MessagesLocalRepositoryImpl
    ): MessagesLocalRepository

}