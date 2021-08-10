package com.sephora.moviesapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


data class MovieCreditsEntity(
    val listId: Int,
    val cast: List<CastEntity>,
) {

    @Parcelize
    @Entity(tableName = "cast_entity")
    class CastEntity(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val nameActor: String,
        val profile_path: Image?,
        val character: String,
        val movieId: Int
    ) : Parcelable

}