package com.lakshay.currencyconverter.data.repository

import com.lakshay.currencyconverter.data.models.CurrencyResponse
import com.lakshay.currencyconverter.data.models.RestClientResult

interface CurrencyRepository {

    suspend fun getRates(base: String): RestClientResult<CurrencyResponse>

}