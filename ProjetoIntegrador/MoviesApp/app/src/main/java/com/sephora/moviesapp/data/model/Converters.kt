package com.sephora.moviesapp.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromImage(image: Image?): String? {
        return image?.url
    }

    @TypeConverter
    fun toImage(url: String?): Image? {
        return url?.let { Image(it) }
    }

    @TypeConverter
    fun listToJson(value: List<GenreModel>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<GenreModel>::class.java).toList()
}