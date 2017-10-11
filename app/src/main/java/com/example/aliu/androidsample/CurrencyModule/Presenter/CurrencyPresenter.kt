package com.example.aliu.androidsample.CurrencyModule

import android.content.Context
import android.content.Intent
import com.example.aliu.androidsample.Storage.CurrencyRoomDatabase
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CurrencyPresenter : CurrencyPresenterInterface {
    var retrofit: Retrofit
    var currencyViewInterface: CurrencyViewInterface? = null
    lateinit var baseCurrency: String
    lateinit var baseDate: String
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


    override fun getCurrencies(baseCurrencyString: String) {
        currencyViewInterface?.emptyAdapter()
        compositeDisposable.add(retrofit.create(CurrencyAPIInterface::class.java).getCurrencies(baseCurrencyString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currencyRoomDatabase?.currencyResponseBodyDAO()?.insertResponseBody(it)
                    baseCurrency = it.baseCurrency ?: ""
                    baseDate = it.date ?: ""
                    val currencyList = getArrayListFromRates(it)
                    currencyViewInterface?.handleRequestedData(currencyList, baseDate, baseCurrency, null)

                }, { error ->
                    val localResponseBody = currencyRoomDatabase?.currencyResponseBodyDAO()?.getResponseBody(baseCurrencyString)
                     localResponseBody?.let {
                        baseCurrency = localResponseBody.baseCurrency ?: ""
                        baseDate = localResponseBody.date ?: ""
                        val currencyList = getArrayListFromRates(localResponseBody)
                        currencyViewInterface?.handleRequestedData(currencyList, baseDate, baseCurrency, error)
                    }

                }, {
                    compositeDisposable.clear()
                }))


    }

    override fun displayCurrencyCalculator(currency: Currency) {
        var intent = Intent(context, CurrencyCalculatorActivity::class.java)
        intent.putExtra("comparingCurrency", currency.currencyType)
        intent.putExtra("value", currency.value)
        intent.putExtra("date", baseDate)
        intent.putExtra("baseCurrency", baseCurrency)
        context.startActivity(intent)
    }

    private fun getArrayListFromRates(body: CurrencyResponseBody): ArrayList<Currency> {
        var currencyList = ArrayList<Currency>()
        body.rates?.map {
            val currency = Currency(it.key , it.value as Double)
            currencyList.add(currency)
        }
        return currencyList
    }


}

