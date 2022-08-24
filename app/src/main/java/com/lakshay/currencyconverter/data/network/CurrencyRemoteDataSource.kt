package com.lakshay.currencyconverter.data.network

import com.lakshay.currencyconverter.data.BaseDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRemoteDataSource @Inject constructor(
    private val currencyService: CurrencyService
) : BaseDataSource(){

    suspend fun getRates(base: String) = getResult {
        currencyService.getRates(base)
    }
}