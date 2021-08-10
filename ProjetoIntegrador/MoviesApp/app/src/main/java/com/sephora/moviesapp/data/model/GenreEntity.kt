package com.sephora.moviesapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

class GenreListEntity(
    val genreResults: List<GenreEntity>
){
    @Parcelize
    @Entity(tableName = "genre_entity")
    class GenreEntity (
        @PrimaryKey(autoGenerate = true) val id: Int,
        val genreName : String,
        val genreId : Int,
    ) : Parcelable
}


