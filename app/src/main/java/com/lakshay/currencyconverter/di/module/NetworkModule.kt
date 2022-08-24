package com.lakshay.currencyconverter.di.module

import android.util.Log
import com.lakshay.currencyconverter.data.exception.NoConnectivityException
import com.lakshay.currencyconverter.di.qualifiers.CurrencyLoggingInterceptor
import com.lakshay.currencyconverter.di.qualifiers.CurrencyNetworkInterceptor
import com.lakshay.currencyconverter.util.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val CONNECTION_TIMEOUT: Long = 60
        private const val WRITE_TIMEOUT: Long = 60
        private const val READ_TIMEOUT: Long = 60

        @Provides
        @Singleton
        fun okHttpClient(
            @CurrencyLoggingInterceptor loggingInterceptor: Interceptor,
            @CurrencyNetworkInterceptor networkInterceptor: Interceptor,
        ): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(networkInterceptor)
                .addInterceptor(loggingInterceptor)
            return builder.build()
        }

        @Provides
        @Singleton
        @CurrencyLoggingInterceptor
        fun loggingInterceptor(): Interceptor {
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                Log.d("TAG", message)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor
        }


        @Provides
        @Singleton
        @CurrencyNetworkInterceptor
        fun networkInterceptor(networkUtil: NetworkUtil): Interceptor {
            return Interceptor { chain ->
                val request = chain.request()
                if (!networkUtil.isOnline()) {
                    throw NoConnectivityException()
                }
                return@Interceptor chain.proceed(request)
            }
        }
    }
}