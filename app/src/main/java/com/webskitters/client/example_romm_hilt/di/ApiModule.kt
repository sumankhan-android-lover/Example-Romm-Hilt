package com.webskitters.client.example_romm_hilt.di

import android.content.Context
import com.webskitters.client.example_romm_hilt.data.network.ApiService
import com.webskitters.client.example_romm_hilt.data.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): ApiService {
        return remoteDataSource.buildApi(ApiService::class.java, context)
    }
}