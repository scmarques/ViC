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

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepositoryImp
@Inject constructor(private val moviesDao: MoviesDao,
                    private val tmdbService: TmdbService
) {

    val mapper = MoviesMapper(moviesDao)

    fun selectAllFavorite(genreId : String?): Flowable<PagingData<DetailedMovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false,
                maxSize = 10,
                prefetchDistance = 3,
                initialLoadSize = 10),
            pagingSourceFactory = { moviesDao.selectAllFavorites(genreId) }
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

    fun getParentalGuidance(movieId: Int) : ParentalGuidanceEntity{
      return moviesDao.getParentalGuidance(movieId)
    }

    fun getGenreList() : List<GenreListEntity.GenreEntity>{
        return moviesDao.getGenreList()
    }

    fun getCastList(movieId: Int) : List<MovieCreditsEntity.CastEntity>{
        return moviesDao.getCastList(movieId)
    }


    fun insertGenreList(genreListEntity: List<GenreListEntity.GenreEntity>) {
        moviesDao.insertGenreList(genreListEntity)
    }


    fun treatGenreEntity () {

        val compositeDisposable = CompositeDisposable()
        compositeDisposable.addAll(
            tmdbService.getGenresList()
                .subscribeOn(Schedulers.io())
                .map { mapper.transformGenreList(it) }
                .map { moviesDao.insertGenreList(it) }
                .doOnError { throw Exception() }
                .subscribe()
        )
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
                .doOnError { throw Exception() }
                .subscribe()
        )

        compositeDisposable.addAll(
            tmdbService.getMovieCredits(movieId)
                .subscribeOn(Schedulers.io())
                .map { mapper.transformCast(movieId, it) }
                .map { moviesDao.insertCastList(it.cast) }
                .doOnError { throw Exception() }
                .subscribe()
        )

        compositeDisposable.addAll(
            tmdbService.getReleaseDateResponse(movieId)
                .subscribeOn(Schedulers.io())
                .map { mapper.transformParentalGuidance(movieId, it) }
                .map { moviesDao.insertParentalGuidance(it) }
                .doOnError { throw Exception() }
                .subscribe()
        )
    }

}