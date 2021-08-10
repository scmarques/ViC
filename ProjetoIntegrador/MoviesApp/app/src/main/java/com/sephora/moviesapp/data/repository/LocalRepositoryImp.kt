package com.sephora.moviesapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.sephora.moviesapp.data.api.TmdbService
import com.sephora.moviesapp.data.database.MoviesDao
import com.sephora.moviesapp.data.model.*
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepositoryImp
@Inject constructor(private val moviesDao: MoviesDao,
                    private val tmdbService: TmdbService
) {

    fun selectAllFavorite(): Flowable<PagingData<DetailedMovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false,
                maxSize = 10,
                prefetchDistance = 3,
                initialLoadSize = 10),
            pagingSourceFactory = { moviesDao.selectAllFavorites() }
        ).flowable
    }

    fun isFavorite(movieId: Int): List<DetailedMovieEntity> {
        return moviesDao.isFavorite(movieId = movieId)
    }

    fun getFavorite(movieId: Int): DetailedMovieEntity {
        return moviesDao.getFavorite(movieId = movieId)
    }

    fun searchInFavorites(title: String): Flowable<PagingData<DetailedMovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false,
                maxSize = 10,
                prefetchDistance = 3,
                initialLoadSize = 10),
            pagingSourceFactory = { moviesDao.searchInFavorite(title) }
        ).flowable
    }

    fun deleteFavorite(movieId: Int) {
        moviesDao.deleteFavorite(movieId)
    }

    fun insertParentalGuidance(parentalGuidance: ParentalGuidanceEntity) {
        moviesDao.insertParentalGuidance(parentalGuidance)
    }

    fun getParentalGuidance(movieId: Int) : ParentalGuidanceEntity{
      return moviesDao.getParentalGuidance(movieId)
    }

    fun getGenreList() : List<GenreListEntity.GenreEntity>{
        return moviesDao.getGenreList()
    }

    fun insertGenreList(genreListEntity: List<GenreListEntity.GenreEntity>) {
        moviesDao.insertGenreList(genreListEntity)
    }

    fun getCastList(movieId: Int) : List<MovieCreditsEntity.CastEntity>{
        return moviesDao.getCastList(movieId)
    }

    fun insertCastList(castEntity: List<MovieCreditsEntity.CastEntity>) {
        moviesDao.insertCastList(castEntity)
    }


    fun changeFavoriteStatus(movieId : Int, isFavorite : Boolean) {
        when (isFavorite) {
            true -> {
                CoroutineScope(Dispatchers.IO).launch {
                    moviesDao.deleteFavorite(movieId)
                }
            }
            false -> {
                treatEntity(movieId = movieId)
            }
        }
    }

    fun treatEntity (movieId : Int){

        val compositeDisposable = CompositeDisposable()
        val mapper = MoviesMapper(moviesDao)
        compositeDisposable.addAll(
            tmdbService.getDetailedMovie(movieId)
                .subscribeOn(Schedulers.io())
                .map { mapper.transformDetailed(it) }
                .map {mapper.transformFavoriteStatus(it, 1)}
                .map { moviesDao.insertFavorite(it) }
                .subscribe()
        )

        compositeDisposable.addAll(
            tmdbService.getMovieCredits(movieId)
                .subscribeOn(Schedulers.io())
                .map { mapper.transformCast(movieId, it) }
                .map { moviesDao.insertCastList(it.cast) }
                .subscribe()
        )

        compositeDisposable.addAll(
            tmdbService.getReleaseDateResponse(movieId)
                .subscribeOn(Schedulers.io())
                .map { mapper.transformParentalGuidance(movieId, it) }
                .map { moviesDao.insertParentalGuidance(it) }
                .subscribe()
        )
    }

    fun checkInDatabase(movieId: Int): Int {
        var isFavorite = false

        moviesDao.isFavorite(movieId)
            .map {
                isFavorite = true
            }
        return if (isFavorite) 1 else 0
    }
}