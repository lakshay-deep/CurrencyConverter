package com.lakshay.currencyconverter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lakshay.currencyconverter.data.models.CurrencyResponse
import com.lakshay.currencyconverter.data.models.RestClientResult
import com.lakshay.currencyconverter.data.repository.CurrencyRepository
import com.lakshay.currencyconverter.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val dispatcher: DispatcherProvider
) : ViewModel(){

    private val _currencyLiveData = MutableLiveData<RestClientResult<CurrencyResponse>>()
    val currencyLiveData: LiveData<RestClientResult<CurrencyResponse>>
        get() = _currencyLiveData


    fun convert(
        amountStr: String,
        fromCurrency: String,
        toCurrency: String
    ) {
        val fromAmount = amountStr.toFloatOrNull()
        if (fromAmount == null){
            _currencyLiveData.postValue(RestClientResult.error("Not a valid amount"))
            return
        }

        viewModelScope.launch(dispatcher.io) {
            val result = currencyRepository.getRates(toCurrency, fromCurrency)
            _currencyLiveData.postValue(result)

        }
    }
}