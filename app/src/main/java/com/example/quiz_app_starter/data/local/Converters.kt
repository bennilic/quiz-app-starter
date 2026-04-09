package com.example.quiz_app_starter.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(list: List<String>?): String? =
        list?.let { gson.toJson(it) }

    @TypeConverter
    fun toStringList(json: String?): List<String>? =
        json?.let { gson.fromJson(it, object : TypeToken<List<String>>() {}.type) }
}
