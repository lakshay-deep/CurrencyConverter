package com.lakshay.currencyconverter.data.repository

import com.lakshay.currencyconverter.data.models.CurrencyResponse
import com.lakshay.currencyconverter.data.models.RestClientResult

interface CurrencyRepository {

    suspend fun getRates(symbols : String, base: String): RestClientResult<CurrencyResponse>

}