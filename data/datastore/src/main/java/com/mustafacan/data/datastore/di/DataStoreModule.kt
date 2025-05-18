package com.mustafacan.data.datastore.di


import android.content.Context
import com.mustafacan.data.datastore.manager.PreferencesDataStoreManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun providePreferencesDataStoreManager(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): PreferencesDataStoreManager {
        return PreferencesDataStoreManager(context, moshi)
    }
}