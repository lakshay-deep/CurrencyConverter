package com.lakshay.currencyconverter.di.module

import android.content.Context
import android.net.ConnectivityManager
import com.lakshay.currencyconverter.data.network.CurrencyService
import com.lakshay.currencyconverter.data.repository.CurrencyRepository
import com.lakshay.currencyconverter.data.repository.CurrencyRepositoryImpl
import com.lakshay.currencyconverter.util.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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

        @Provides
        @Singleton
        fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
            override val unconfined: CoroutineDispatcher
                get() = Dispatchers.Unconfined
        }
    }

    @Binds
    @Singleton
    abstract fun provideCurrencyRepository(currencyRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository
}