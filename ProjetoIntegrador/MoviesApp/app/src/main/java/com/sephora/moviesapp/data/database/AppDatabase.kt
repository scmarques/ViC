package com.sephora.moviesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sephora.moviesapp.data.model.*

@Database(entities = [DetailedMovieEntity::class, ParentalGuidanceEntity::class,
    MovieCreditsEntity.CastEntity::class, GenreListEntity.GenreEntity::class],
    version = 2, exportSchema = false)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase () {

    abstract fun moviesDao(): MoviesDao
}