package com.tiooooo.core.di

import com.tiooooo.core.common.ContextProvider
import com.tiooooo.core.constant.Constants
import com.tiooooo.core.network.provider.NetworkProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkProvider(
        contextProvider: ContextProvider,
    ): NetworkProvider {
        return NetworkProvider(
            contextProvider = contextProvider,
            url = Constants.BASE_URL,
        )
    }

    @Singleton
    @Provides
    fun provideRetrofit(networkProvider: NetworkProvider): Retrofit {
        return networkProvider.createRetrofit()
    }
}