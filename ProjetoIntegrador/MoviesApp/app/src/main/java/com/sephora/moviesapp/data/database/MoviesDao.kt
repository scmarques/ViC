package com.sephora.moviesapp.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.sephora.moviesapp.data.model.*

@Dao
interface MoviesDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertFavorite(movie : DetailedMovieEntity)

        @Query("SELECT * FROM favorites ORDER BY title ASC")
        fun selectAllFavorites(): PagingSource<Int, DetailedMovieEntity>

        @Query("SELECT * FROM favorites WHERE movieId = :movieId")
        fun isFavorite(movieId : Int) : List<DetailedMovieEntity>

        @Query("SELECT * FROM favorites WHERE title LIKE '%' || :title || '%'")
        fun searchInFavorite(title : String?) : PagingSource<Int, DetailedMovieEntity>

        @Query("SELECT * FROM favorites WHERE movieId = :movieId")
        fun getFavorite(movieId : Int) : DetailedMovieEntity

        @Query("DELETE FROM favorites WHERE movieId = :movieId")
        fun deleteFavorite(movieId: Int)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertParentalGuidance(parentalGuidance: ParentalGuidanceEntity)

        @Query("SELECT * FROM parental_guidance WHERE movieId = :movieId")
        fun getParentalGuidance(movieId : Int) : ParentalGuidanceEntity

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertCastList(castEntity: List<MovieCreditsEntity.CastEntity>)

        @Query("SELECT * FROM cast_entity WHERE movieId = :movieId")
        fun getCastList(movieId : Int) : List<MovieCreditsEntity.CastEntity>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertGenreList(genreListEntity: List<GenreListEntity.GenreEntity>)

        @Query("SELECT * FROM genre_entity")
        fun getGenreList() : List<GenreListEntity.GenreEntity>

}