package com.example.aliu.androidsample.CurrencyModule

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.aliu.androidsample.CurrencyModule.View.CurrencyBaseSelectorActivity
import com.example.aliu.androidsample.R
import kotlinx.android.synthetic.main.currency_fragment.*


class CurrencyFragment : Fragment(), CurrencyViewInterface {
    var presenter: CurrencyPresenterInterface? = null
    private lateinit var adapter: CurrencyRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)


        val rootView = inflater!!.inflate(R.layout.currency_fragment, container, false)

        val baseButton = rootView.findViewById<Button>(R.id.baseButton)
        baseButton.setOnClickListener {
            displayBaseList()
        }


        adapter = CurrencyRecyclerViewAdapter(this)
        val currencyRecyclerView = rootView.findViewById<RecyclerView>(R.id.currencyRecyclerView)
        currencyRecyclerView.adapter = adapter
        currencyRecyclerView.layoutManager = LinearLayoutManager(context)

        return rootView
    }

    override fun onStart() {
        super.onStart()
        presenter?.getCurrencies(null)
        progressBar.visibility = View.VISIBLE
    }


    override fun handleRequestedData(throwable: Throwable?) {

        throwable?.let {
            Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
        }

        progressBar.visibility = View.INVISIBLE
        adapter.addData(presenter?.currencies ?: ArrayList())
        val base = presenter?.baseCurrency
        val date = presenter?.baseDate
        baseTextView.text = "BASE: $base"
        dateTextView.text = "AS OF: $date"

        ////// configure button

        baseButton.text = base



    }

    override fun emptyAdapter() {
        adapter.emptyData()
    }

    override fun displayCurrencyCalculator(currency: Currency) {
        val intent = Intent(context, CurrencyCalculatorActivity::class.java)
        intent.putExtra("comparingCurrency", currency.currencyType)
        intent.putExtra("value", currency.value)
        intent.putExtra("date", presenter?.baseDate)
        intent.putExtra("baseCurrency", presenter?.baseCurrency)
        context.startActivity(intent)
    }

     override fun displayBaseList() {
        val intent = Intent(context, CurrencyBaseSelectorActivity::class.java)
        val bundle = Bundle()
        bundle.putStringArrayList("data", ArrayList(presenter?.currencies?.map {it.currencyType}))
        intent.putExtras(bundle)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val thing = data?.extras?.getString("base")
            thing?.let {
                presenter?.getCurrencies(it)
            }
        }

    }

}

