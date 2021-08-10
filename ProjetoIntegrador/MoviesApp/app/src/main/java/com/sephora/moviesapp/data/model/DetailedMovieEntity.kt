package com.sephora.moviesapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

data class MoviesList ( val page : Int,
                             val pages : Int,
                             val moviesListEntity : List<DetailedMovieEntity>)

@Entity(
    tableName = "favorites",
    foreignKeys = [ForeignKey(
        entity = DetailedMovieEntity::class,
        parentColumns = arrayOf("movieId"),
        childColumns = arrayOf("movieId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)

class DetailedMovieEntity(
    var isFavorite: Int?,
    val posterPath: Image?,
    val title: String,
    val runtime: String?,
    val genreResults: List<GenreModel>?,
    val voteAverage: String,
    val movieOverview: String?,
    @PrimaryKey(autoGenerate = false) val movieId: Int,
    val backdropPath: Image?,
    val releaseYear: String?
)