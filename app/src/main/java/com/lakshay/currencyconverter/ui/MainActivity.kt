package com.lakshay.currencyconverter.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.jaeger.library.StatusBarUtil
import com.lakshay.currencyconverter.R
import com.lakshay.currencyconverter.data.models.Rates
import com.lakshay.currencyconverter.data.models.RestClientResult
import com.lakshay.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlin.math.round

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarColor()
        setListeners()
        observeLiveData()

    }

    private fun setStatusBarColor() {
        StatusBarUtil.setColorNoTranslucent(
            this,
            ContextCompat.getColor(this, android.R.color.black)
        )
    }

    private fun setListeners(){
        binding.btnConvert.setOnClickListener {
            getData()
        }
    }

    private fun getData() {
        viewModel.convert(
            binding.etFrom.text.toString(),
            binding.spFromCurrency.selectedItem.toString(),
            binding.spToCurrency.selectedItem.toString()
        )
    }

    private fun observeLiveData(){
        viewModel.currencyLiveData.observe(this){
            when(it.status){
                RestClientResult.Status.LOADING -> {
                    binding.progressBar.isVisible = true
                }
                RestClientResult.Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    binding.tvResult.setTextColor(Color.BLACK)
                    binding.tvResult.text = it.data!!.rates.toString()
                    val rates = it.data!!.rates
                    val toCurrency = binding.spToCurrency.selectedItem.toString()
                    val rate = getRateFromCurrency(toCurrency, rates)
                    if (rate == null){
                        Toasty.error(this, "Unexpected Error", Toasty.LENGTH_SHORT).show()
                    } else {
                        val fromAmount = binding.spFromCurrency.selectedItem.toString()
                        val convertedCurrency = round(fromAmount!!.toFloat() * rate!!.toString().toFloat() * 100) / 100
                        binding.tvResult.text = convertedCurrency.toString()
                    }

                }
                RestClientResult.Status.ERROR -> {
                    binding.progressBar.isVisible = false
                    binding.tvResult.setTextColor(Color.RED)
                    binding.tvResult.text = it.errorBody.toString()
                }
            }
        }
    }

    private fun getRateFromCurrency(currency: String,rates: Rates) = when(currency) {
        "AED"->rates.AED
        "AFN"->rates.AFN
        "ALL"->rates.ALL
        "AMD"->rates.AMD
        "ANG"->rates.ANG
        "AOA"->rates.AOA
        "ARS"->rates.ARS
        "AUD"->rates.AUD
        "AWG"->rates.AWG
        "AZN"->rates.AZN
        "BAM"->rates.BAM
        "BBD"->rates.BBD
        "BDT"->rates.BDT
        "BGN"->rates.BGN
        "BHD"->rates.BHD
        "BIF"->rates.BIF
        "BMD"->rates.BMD
        "BND"->rates.BND
        "BOB"->rates.BOB
        "BRL"->rates.BRL
        "BSD"->rates.BSD
        "BTC"->rates.BTC
        "BTN"->rates.BTN
        "BWP"->rates.BWP
        "BYN"->rates.BYN
        "BYR"->rates.BYR
        "BZD"->rates.BZD
        "CAD"->rates.CAD
        "CDF"->rates.CDF
        "CHF"->rates.CHF
        "CLF"->rates.CLF
        "CLP"->rates.CLP
        "CNY"->rates.CNY
        "COP"->rates.COP
        "CRC"->rates.CRC
        "CUC"->rates.CUC
        "CUP"->rates.CUP
        "CVE"->rates.CVE
        "CZK"->rates.CZK
        "DJF"->rates.DJF
        "DKK"->rates.DKK
        "DOP"->rates.DOP
        "DZD"->rates.DZD
        "EGP"->rates.EGP
        "ERN"->rates.ERN
        "ETB"->rates.ETB
        "EUR"->rates.EUR
        "FJD"->rates.FJD
        "FKP"->rates.FKP
        "GBP"->rates.GBP
        "GEL"->rates.GEL
        "GGP"->rates.GGP
        "GHS"->rates.GHS
        "GMD"->rates.GMD
        "GNF"->rates.GNF
        "GTQ"->rates.GTQ
        "GYD"->rates.GYD
        "HKD"->rates.HKD
        "HNL"->rates.HNL
        "HRK"->rates.HRK
        "HTG"->rates.HTG
        "HUF"->rates.HUF
        "IDR"->rates.IDR
        "ILS"->rates.ILS
        "IMP"->rates.IMP
        "INR"->rates.INR
        "IQD"->rates.IQD
        "IRR"->rates.IRR
        "ISK"->rates.ISK
        "JEP"->rates.JEP
        "JMD"->rates.JMD
        "JOD"->rates.JOD
        "JPY"->rates.JPY
        "KES"->rates.KES
        "KGS"->rates.KGS
        "KHR"->rates.KHR
        "KMF"->rates.KMF
        "KPW"->rates.KPW
        "KRW"->rates.KRW
        "KYD"->rates.KYD
        "KZT"->rates.KZT
        "LAK"->rates.LAK
        "LBP"->rates.LBP
        "LKR"->rates.LKR
        "LRD"->rates.LRD
        "LSL"->rates.LSL
        "LTL"->rates.LTL
        "LVL"->rates.LVL
        "LYD"->rates.LYD
        "MAD"->rates.MAD
        "MDL"->rates.MDL
        "MGA"->rates.MGA
        "MKD"->rates.MKD
        "MMK"->rates.MMK
        "MNT"->rates.MNT
        "MOP"->rates.MOP
        "MRO"->rates.MRO
        "MUR"->rates.MUR
        "MVR"->rates.MVR
        "MWK"->rates.MWK
        "MXN"->rates.MXN
        "MYR"->rates.MYR
        "MZN"->rates.MZN
        "NAD"->rates.NAD
        "NGN"->rates.NGN
        "NIO"->rates.NIO
        "NOK"->rates.NOK
        "NPR"->rates.NPR
        "NZD"->rates.NZD
        "OMR"->rates.OMR
        "PAB"->rates.PAB
        "PEN"->rates.PEN
        "PGK"->rates.PGK
        "PHP"->rates.PHP
        "PKR"->rates.PKR
        "PLN"->rates.PLN
        "PYG"->rates.PYG
        "QAR"->rates.QAR
        "RON"->rates.RON
        "RSD"->rates.RSD
        "RUB"->rates.RUB
        "RWF"->rates.RWF
        "SAR"->rates.SAR
        "SBD"->rates.SBD
        "SCR"->rates.SCR
        "SDG"->rates.SDG
        "SEK"->rates.SEK
        "SGD"->rates.SGD
        "SHP"->rates.SHP
        "SOS"->rates.SOS
        "SRD"->rates.SRD
        "STD"->rates.STD
        "SVC"->rates.SVC
        "SYP"->rates.SYP
        "SZL"->rates.SZL
        "THB"->rates.THB
        "TJS"->rates.TJS
        "TMT"->rates.TMT
        "TND"->rates.TND
        "TOP"->rates.TOP
        "TRY"->rates.TRY
        "TTD"->rates.TTD
        "TWD"->rates.TWD
        "TZS"->rates.TZS
        "UAH"->rates.UAH
        "UGX"->rates.UGX
        "USD"->rates.USD
        "UYU"->rates.UYU
        "UZS"->rates.UZS
        "VEF"->rates.VEF
        "VND"->rates.VND
        "VUV"->rates.VUV
        "WST"->rates.WST
        "XAF"->rates.XAF
        "XAG"->rates.XAG
        "XAU"->rates.XAU
        "XCD"->rates.XCD
        "XDR"->rates.XDR
        "XOF"->rates.XOF
        "XPF"->rates.XPF
        "YER"->rates.YER
        "ZAR"->rates.ZAR
        "ZMK"->rates.ZMK
        "ZMW"->rates.ZMW
        "ZWL"->rates.ZWL
        else -> null
    }
}