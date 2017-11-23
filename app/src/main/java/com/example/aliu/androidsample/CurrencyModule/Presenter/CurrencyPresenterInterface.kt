package com.example.aliu.androidsample.CurrencyModule

import android.content.Context

interface CurrencyPresenterInterface {
    val context: Context
    var baseCurrency: String
    var baseDate: String
    var currencies: ArrayList<Currency>
    fun getCurrencies(baseCurrencyString: String?)
}