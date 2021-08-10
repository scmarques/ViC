package com.sephora.moviesapp.data.repository

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.sephora.moviesapp.data.api.TmdbService
import com.sephora.moviesapp.data.database.MoviesDao
import com.sephora.moviesapp.data.model.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton


@Singleton
class MoviesPagingSource(
    private val tmdbService: TmdbService,
    private val moviesDao: MoviesDao,
    private val with_genres: String?
) : RxPagingSource<Int, DetailedMovieEntity>() {
    override fun getRefreshKey(state: PagingState<Int, DetailedMovieEntity>): Int? {
        TODO()

    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, DetailedMovieEntity>> {
        val position = params.key ?: 1
        return tmdbService.getPopularMovies(
            api_key = Credentials.API_KEY,
            language = "pt-BR",
            page = position,
            with_genres = with_genres)

            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: AllMoviesResponse, position: Int): LoadResult<Int, DetailedMovieEntity> {
        val mapper = MoviesMapper(moviesDao = moviesDao)
        var newData = mapper.transformIntoBasic(data)
        return LoadResult.Page(
            data = newData.moviesListEntity,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.total) null else position + 1
        )
    }
}