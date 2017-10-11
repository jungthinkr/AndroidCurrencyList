package com.example.aliu.androidsample.Storage

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.aliu.androidsample.CurrencyModule.CurrencyResponseBody


@Dao
interface CurrencyResponseBodyDAO {

    @Query("SELECT * FROM currencyDatabase WHERE baseCurrency = :arg0 LIMIT 1")
    fun getResponseBody(baseCurrency: String): CurrencyResponseBody

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResponseBody(currency: CurrencyResponseBody)

    @Query("delete from currencyDatabase")
    fun removeResponseBody()
}