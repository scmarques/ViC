package com.sephora.moviesapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.sephora.moviesapp.data.api.TmdbService
import com.sephora.moviesapp.data.database.MoviesDao
import com.sephora.moviesapp.data.model.*
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepositoryImp @Inject constructor(
    private val tmdbService: TmdbService,
    private val moviesDao: MoviesDao
) {

    private val mapper = MoviesMapper(moviesDao)

    fun fetchAllMoviesResponse(with_genres: String): Flowable<PagingData<DetailedMovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false,
                maxSize = 10,
                initialLoadSize = 15
            ),
            pagingSourceFactory = {
                MoviesPagingSource(
                    tmdbService,
                    moviesDao = moviesDao,
                    with_genres = with_genres
                )
            }
        ).flowable

    }

    fun fetchSearchByQuery(query: String): Flowable<PagingData<DetailedMovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false,
                maxSize = 10,
                initialLoadSize = 15
            ),
            pagingSourceFactory = { MoviesFromSearchPagingSource(tmdbService, moviesDao, query) }
        ).flowable

    }

    fun fetchGenresListResponse(): Flowable<List<GenreListEntity.GenreEntity>> {
        return tmdbService.getGenresList()
            .map { mapper.transformGenreList(it) }

    }

    fun fetchParentalsGuidanceResponse(id: Int): Flowable<ParentalGuidanceEntity> {
        return tmdbService.getReleaseDateResponse(movie_id = id)
            .map { mapper.transformParentalGuidance(id, it) }

    }

    fun fetchDetailedMovieResponse(id: Int): Flowable<DetailedMovieEntity> {
        return tmdbService.getDetailedMovie(movie_id = id)
            .map { mapper.transformDetailed(it) }

    }

    fun fetchMovieCreditsResponse(id: Int): Flowable<MovieCreditsEntity> {
        return tmdbService.getMovieCredits(movie_id = id)
            .map { mapper.transformCast(id, it) }


    }

}
