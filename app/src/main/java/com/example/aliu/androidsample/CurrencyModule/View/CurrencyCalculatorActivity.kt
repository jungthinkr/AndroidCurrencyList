package com.example.aliu.androidsample.CurrencyModule

import android.R.attr.maxLength
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import com.example.aliu.androidsample.R
import kotlinx.android.synthetic.main.currency_calculator_activity.*

class CurrencyCalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_calculator_activity)
        setUpActionBar()
        handleIntentData()
        addFiltersToCurrencyText()
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.abc_ic_ab_back_material, null)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


    private fun handleIntentData() {
        val comparingCurrency = intent.extras.get("comparingCurrency") as String
        val value = intent.extras.get("value") as Double
        val date = intent.extras.get("date") as String
        val baseCurrency = intent.extras.get("baseCurrency") as String
        title = "$baseCurrency/$comparingCurrency as of $date"

        baseCurrencyLabel.text = "$baseCurrency: "
        comparingCurrencyLabel.text = "$comparingCurrency: "
        baseCurrencyInput.requestFocus()
        baseCurrencyInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(changedText: Editable?) {
                if (changedText.isNullOrEmpty()) {
                    comparingCurrencyText.text = "0"
                } else {
                    comparingCurrencyText.text = (value * changedText.toString().toDouble()).toString()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun addFiltersToCurrencyText() {
        baseCurrencyInput.filters = arrayOf(InputFilter.LengthFilter(maxLength), CurrencyDigitsFilter(5, 2))
        comparingCurrencyText.filters = arrayOf(InputFilter.LengthFilter(maxLength), CurrencyDigitsFilter(5, 2))
    }

}