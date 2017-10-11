package com.example.aliu.androidsample.CurrencyModule

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyAPIInterface {

    @GET("/latest")
    fun getCurrencies(@Query("base") baseCurrencyString: String): Observable<CurrencyResponseBody>
}