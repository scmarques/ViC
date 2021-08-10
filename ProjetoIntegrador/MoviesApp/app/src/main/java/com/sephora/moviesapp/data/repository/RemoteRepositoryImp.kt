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
    private val moviesDao: MoviesDao,
    ) {

    fun fetchAllMoviesResponse(with_genres : String) : Flowable<PagingData<DetailedMovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false,
                maxSize = 30,
                initialLoadSize = 1
            ),
            pagingSourceFactory = { MoviesPagingSource(tmdbService,
                moviesDao = moviesDao,
                with_genres = with_genres) }
        ).flowable

    }

    fun fetchSearchByQuery(query : String) : Flowable<PagingData<DetailedMovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false,
                maxSize = 30,
                initialLoadSize = 40
            ),
            pagingSourceFactory = { MoviesFromSearchPagingSource(tmdbService, moviesDao, query) }
        ).flowable

    }

/*
    fun fetchSearchByQuery(query : String) : LiveData<AllMoviesResponse>{
        return tmdbService.searchMovieByQuery(query = query).toLiveData()
    }
*/
    fun fetchGenresListResponse(): Flowable<GenreResponse> {
        return tmdbService.getGenresList()
    }

    fun fetchParentalsGuidanceResponse(id: Int): Flowable<ReleaseDatesResponse> {
        return tmdbService.getReleaseDateResponse(movie_id = id)
    }

    fun fetchDetailedMovieResponse(id: Int): Flowable<DetailedMovieModel> {
        return tmdbService.getDetailedMovie(movie_id = id)

    }

    fun fetchMovieCreditsResponse(id: Int): Flowable<MovieCreditsResponse> {
        return tmdbService.getMovieCredits(movie_id = id)
    }

    }
