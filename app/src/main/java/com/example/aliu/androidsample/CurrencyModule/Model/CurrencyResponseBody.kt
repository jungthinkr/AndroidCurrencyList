package com.example.aliu.androidsample.CurrencyModule

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currencyDatabase")
class CurrencyResponseBody {
    @SerializedName("base")
    @Expose
    @PrimaryKey
    var baseCurrency: String? = null

    @SerializedName("date")
    @Expose

    var date: String? = null

    @SerializedName("rates")
    @Expose
    var rates: HashMap<String, Any>? = null
}
