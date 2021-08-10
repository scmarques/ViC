package com.sephora.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse (
    val id : Int,
    @SerializedName("cast") val castList : List<CastModel>,
)

data class CastModel (
    @SerializedName("name") val nameActor : String,
    val profile_path : String,
    val character : String
)