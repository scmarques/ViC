package com.sephora.moviesapp.data.model


import com.google.gson.annotations.SerializedName

 class DetailedMovieModel(
        val title: String,
        val runtime: Int?,
        @SerializedName("genres") val genreResults: List<GenreModel>,
        @SerializedName("release_date") val releaseDate: String,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("overview") val movieOverview: String?,
        @SerializedName("id") val movieId: Int,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("poster_path") val posterPath: String,
        var isFavorite : Int,
        var allGenres : String
        ) {

        fun runtimeMask(runtime : Int?): String {
            runtime?.let {
                return ("${runtime / 60}h ${runtime % 60}min")
            }
            return ""
        }

        fun getVoteAverageString(voteAverage: Double): String {
            val votePercent = voteAverage * 10
            return "%.0f".format(votePercent) + "%"
        }

        fun getReleaseYear(releaseDate: String): String {
            if (releaseDate.isNotEmpty()) return releaseDate.substring(0, 4)
            else return " "
        }
}