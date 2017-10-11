package com.example.aliu.androidsample.CurrencyModule

import android.content.Context

interface CurrencyPresenterInterface {
    val context: Context
    fun getCurrencies(baseCurrencyString: String)
    fun displayCurrencyCalculator(currency: Currency)
}