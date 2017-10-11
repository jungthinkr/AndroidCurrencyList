package com.example.aliu.androidsample.CurrencyModule

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.aliu.androidsample.R
import com.squareup.picasso.Picasso


class CurrencyRecyclerViewAdapter(private var context: Context, private var presenter: CurrencyPresenterInterface?) : RecyclerView.Adapter<CurrencyRecyclerViewAdapter.ViewHolder>() {
    private var currencies = ArrayList<Currency>()

    class ViewHolder(val view: View, val context: Context, private val presenter: CurrencyPresenterInterface?) : RecyclerView.ViewHolder(view) {

        fun bindCurrency(currency: Currency) {

            view.setOnClickListener {
                presenter?.displayCurrencyCalculator(currency)
            }

            val currencyType = view.findViewById<TextView>(R.id.currencyType)
            val value = view.findViewById<TextView>(R.id.value)
            val imageView = view.findViewById<ImageView>(R.id.currencyImageView)
            Picasso.with(context)
                    .load("http://s.xe.com/themes/xe/images/flags/big/" + currency.currencyType.toLowerCase() + ".png")
                    .placeholder(R.drawable.placeholder)
                    .into(imageView)
            currencyType.text = currency.currencyType
            value.text = currency.value.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.currency_recycler_viewholder, parent, false)

        return CurrencyRecyclerViewAdapter.ViewHolder(v, context, presenter)
    }

    override fun onBindViewHolder(holder: CurrencyRecyclerViewAdapter.ViewHolder, position: Int) {
        currencies.let {
            holder.bindCurrency(currencies[position])
        }
    }

    override fun getItemCount(): Int {
        return currencies.count()
    }

    fun addData(data: ArrayList<Currency>) {
        currencies.addAll(data)
        notifyDataSetChanged()
    }


    fun emptyData() {
        currencies.clear()
        notifyDataSetChanged()
    }


}
