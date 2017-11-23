package com.example.aliu.androidsample.CurrencyModule

import android.content.Context

interface CurrencyViewInterface {
    fun handleRequestedData(throwable: Throwable?)
    fun emptyAdapter()
    fun displayBaseList()
    fun displayCurrencyCalculator(currency: Currency)
}