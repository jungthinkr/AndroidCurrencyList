package com.example.aliu.androidsample.CurrencyModule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.aliu.androidsample.CurrencyModule.View.CurrencyBaseSelectorActivity
import com.example.aliu.androidsample.Storage.CurrencyRoomDatabase
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CurrencyPresenter : CurrencyPresenterInterface {
    var retrofit: Retrofit
    override var currencies: ArrayList<Currency> = ArrayList()
    var currencyViewInterface: CurrencyViewInterface? = null
    override lateinit var baseCurrency: String
    override lateinit var baseDate: String
    override var context: Context
    var compositeDisposable = CompositeDisposable()
    private  val currencyRoomDatabase: CurrencyRoomDatabase?
    constructor(context: Context) {
        this.context = context
        this.currencyRoomDatabase = CurrencyRoomDatabase.Companion.getDatabase(context)

        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://api.fixer.io").build()
    }


     override fun getCurrencies(baseCurrencyString: String?) {
        currencyViewInterface?.emptyAdapter()
         val base = baseCurrencyString ?: "USD"
        compositeDisposable.add(retrofit.create(CurrencyAPIInterface::class.java).getCurrencies(base)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currencyRoomDatabase?.currencyResponseBodyDAO()?.insertResponseBody(it)
                    baseCurrency = it.baseCurrency ?: ""
                    baseDate = it.date ?: ""
                    currencies = getArrayListFromRates(it)
                    currencyViewInterface?.handleRequestedData(null)

                }, { error ->
                    val localResponseBody = currencyRoomDatabase?.currencyResponseBodyDAO()?.getResponseBody(base)
                     localResponseBody?.let {
                        baseCurrency = localResponseBody.baseCurrency ?: ""
                        baseDate = localResponseBody.date ?: ""
                        this.currencies = getArrayListFromRates(localResponseBody)
                        currencyViewInterface?.handleRequestedData(error)
                    }

                }, {
                    compositeDisposable.clear()
                }))

    }



    private fun getArrayListFromRates(body: CurrencyResponseBody): ArrayList<Currency> {
        val currencyList = ArrayList<Currency>()
        body.rates?.map {
            val currency = Currency(it.key , it.value as Double)
            currencyList.add(currency)
        }
        return currencyList
    }


}

