package com.lakshay.currencyconverter.data.repository

import com.lakshay.currencyconverter.data.network.CurrencyRemoteDataSource
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource
) : CurrencyRepository {

    override suspend fun getRates(symbols : String, base: String) = currencyRemoteDataSource.getRates(symbols, base)

}