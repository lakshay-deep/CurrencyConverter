package com.lakshay.currencyconverter.data.network

import com.lakshay.currencyconverter.data.models.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyService {

    @Headers("apikey: k4k5X5jXqRlzpnLRjFz2E0ZEqTk4Xdwe")
    @GET("latest")
     suspend fun getRates(
        @Query("symbols") symbols : String,
        @Query("base") base : String,
    ) : Response<CurrencyResponse>
}