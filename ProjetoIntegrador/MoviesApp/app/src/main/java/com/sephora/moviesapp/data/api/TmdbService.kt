package com.sephora.moviesapp.data.api

import com.sephora.moviesapp.data.model.*
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    @GET("discover/movie")
    fun getPopularMovies(
        @Query("api_key") api_key: String = Credentials.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int,
        @Query("with_genres") with_genres : String?
    ): Single<AllMoviesResponse>

    @GET("genre/movie/list")
    fun getGenresList(
        @Query("api_key") api_key: String = Credentials.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): Flowable<GenreResponse>

    @GET("search/movie")
    fun searchMovieByQuery(
        @Query("api_key") api_key: String = Credentials.API_KEY,
        @Query("language") language: String = "pt-BR",
        @Query("query") query: String
    ): Single<AllMoviesResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = Credentials.API_KEY
    ) : Flowable<MovieCreditsResponse>

    @GET("movie/{movie_id}")
    fun getDetailedMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = Credentials.API_KEY,
        @Query("language") language: String = "pt-BR"
    ): Flowable<DetailedMovieModel>

    @GET("movie/{movie_id}/release_dates")
    fun getReleaseDateResponse(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String = Credentials.API_KEY
    ): Flowable<ReleaseDatesResponse>
}