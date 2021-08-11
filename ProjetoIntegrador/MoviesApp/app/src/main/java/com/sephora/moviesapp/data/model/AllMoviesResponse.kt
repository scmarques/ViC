package com.sephora.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class AllMoviesResponse (
    val page : Int,
    @SerializedName("results")  val movies : List<MovieModel>,
    @SerializedName("total_pages") val total : Int
)

class MovieModel(
    var isFavorite : Int,
    val title : String,
    @SerializedName ("poster_path") val posterPath : String,
    @SerializedName ("vote_average") val voteAverage: Double,
    @SerializedName ("id") val movieId : Int,
    @SerializedName ("genre_ids") val genreId : List<Int>
) {

    fun getVoteAverageString(voteAverage : Double): String {
        val votePercent = voteAverage * 10
        return "%.0f".format(votePercent) + "%"
    }

}
