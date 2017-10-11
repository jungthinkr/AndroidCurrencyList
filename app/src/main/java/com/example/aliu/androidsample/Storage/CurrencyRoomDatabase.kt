package com.example.aliu.androidsample.Storage

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.aliu.androidsample.CurrencyModule.CurrencyResponseBody


@Database(entities = arrayOf(CurrencyResponseBody::class),
        version = 1,
        exportSchema = false)
@TypeConverters(Converters::class)

abstract class CurrencyRoomDatabase: RoomDatabase() {
    abstract fun currencyResponseBodyDAO(): CurrencyResponseBodyDAO
    companion object {
        private var INSTANCE: CurrencyRoomDatabase? = null
        @JvmStatic fun getDatabase(context: Context): CurrencyRoomDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, CurrencyRoomDatabase::class.java, "currencyDatabase")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE
        }
    }
}