package com.example.aliu.androidsample.Storage

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {


    companion object {
        @TypeConverter
        @JvmStatic fun fromHashmap(map: HashMap<String, Any>): String {
            return Gson().toJson(map)
        }

        @TypeConverter
        @JvmStatic fun fromJson(value: String):  HashMap<String, Any> {
            val map = object: TypeToken<HashMap<String, Any>>() {
            }.type
            return Gson().fromJson(value, map)
        }

    }

}