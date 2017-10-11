package com.example.aliu.androidsample


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.aliu.androidsample.CurrencyModule.CurrencyFragment
import com.example.aliu.androidsample.CurrencyModule.CurrencyPresenter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayCurrencyFragment()

    }

    fun displayCurrencyFragment() {
        val currencyFragment = CurrencyFragment()
        val presenter = CurrencyPresenter(this)
        presenter.currencyViewInterface = currencyFragment
        currencyFragment.presenter = presenter
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, currencyFragment).commit()
    }
}
