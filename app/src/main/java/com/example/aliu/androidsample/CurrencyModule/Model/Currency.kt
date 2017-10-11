package com.example.aliu.androidsample.CurrencyModule

class Currency {

    var currencyType: String
    var value: Double

    constructor(currencyType: String, value: Double) {
        this.currencyType = currencyType
        this.value = value
    }
}