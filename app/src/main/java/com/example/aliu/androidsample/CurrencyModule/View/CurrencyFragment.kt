package com.example.aliu.androidsample.CurrencyModule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.example.aliu.androidsample.R
import kotlinx.android.synthetic.main.currency_fragment.*


enum class SpinnerOption {
    USD,
    EUR,
    KRW,
    GBP,
    RUB
}


class CurrencyFragment : Fragment(), CurrencyViewInterface {
    var presenter: CurrencyPresenterInterface? = null
    private lateinit var spinner: Spinner
    private lateinit var adapter: CurrencyRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)



        val rootView = inflater!!.inflate(R.layout.currency_fragment, container, false)
        val progressBar = rootView.findViewById<ProgressBar>(R.id.progressBar)
        spinner = rootView.findViewById(R.id.currencySpinner)
        val spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.currency_picker, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                progressBar.visibility = View.VISIBLE
                presenter?.getCurrencies(SpinnerOption.values()[position].toString())
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

        adapter = CurrencyRecyclerViewAdapter(context, presenter)
        val currencyRecyclerView = rootView.findViewById<RecyclerView>(R.id.currencyRecyclerView)
        currencyRecyclerView.adapter = adapter
        currencyRecyclerView.layoutManager = LinearLayoutManager(context)

        return rootView
    }

    override fun onStart() {
        super.onStart()
        progressBar.visibility = View.VISIBLE
        presenter?.getCurrencies(SpinnerOption.values()[spinner.selectedItemPosition].toString())

    }


    override fun handleRequestedData(currencies: ArrayList<Currency>, date: String, base: String, throwable: Throwable?) {

        throwable?.let {
            Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
        }

            progressBar.visibility = View.INVISIBLE
            adapter.addData(currencies)
            baseTextView.text = "BASE: $base"
            dateTextView.text = "AS OF: $date"


    }

    override fun emptyAdapter() {
        adapter.emptyData()
    }


}

