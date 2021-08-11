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
/*
    @TypeConverter
    fun listIntToJson(value: List<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToIntList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()

    @TypeConverter
    fun castListToJson(value: List<MovieCreditsEntity.CastEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToCastList(value: String) =
        Gson().fromJson(value, Array<MovieCreditsEntity.CastEntity>::class.java).toList()

    @TypeConverter
    fun genreListToJson(value: List<GenreListEntity.GenreEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToGenreList(value: String) =
        Gson().fromJson(value, Array<GenreListEntity.GenreEntity>::class.java).toList()*/
}