package com.lakshay.newsapp.di.module

import android.content.Context
import android.net.ConnectivityManager
import com.lakshay.currencyconverter.data.network.CurrencyService
import com.lakshay.currencyconverter.data.repository.CurrencyRepository
import com.lakshay.currencyconverter.data.repository.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object{

        @Provides
        @Singleton
        fun provideCurrencyService(retrofit: Retrofit): CurrencyService{
            return retrofit.create(CurrencyService::class.java)
        }

        @Provides
        @Singleton
        fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
    }

    @Binds
    @Singleton
    abstract fun provideCurrencyRepository(newsRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository
}