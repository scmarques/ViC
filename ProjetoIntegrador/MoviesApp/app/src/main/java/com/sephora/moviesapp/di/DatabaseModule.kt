package com.sephora.moviesapp.di

import android.app.Application
import androidx.room.Room
import com.sephora.moviesapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun buildDatabase(app: Application) =
        Room.databaseBuilder(app, AppDatabase::class.java, "moviesDB")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMoviesDao(database: AppDatabase) = database.moviesDao()


}
