package com.example.aliu.androidsample.CurrencyModule

interface CurrencyViewInterface {
    fun handleRequestedData(currencies: ArrayList<Currency>, date: String, base: String, throwable: Throwable?)
    fun emptyAdapter()
}