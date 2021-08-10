package com.sephora.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class GenreResponse (
    @SerializedName("genres")  val genreResults : List<GenreModel>
)

data class GenreModel (
    val name : String,
    @SerializedName("id") val genreId : Int,
)