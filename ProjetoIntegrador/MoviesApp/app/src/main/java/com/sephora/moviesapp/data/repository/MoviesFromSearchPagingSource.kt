package com.sephora.moviesapp.data.repository

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.sephora.moviesapp.data.api.TmdbService
import com.sephora.moviesapp.data.database.MoviesDao
import com.sephora.moviesapp.data.model.*
import com.sephora.moviesapp.data.repository.LocalRepositoryImp
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MoviesFromSearchPagingSource(
    private val tmdbService: TmdbService,
    private val moviesDao: MoviesDao,
    private val query: String
    ) : RxPagingSource<Int, DetailedMovieEntity>() {
        override fun getRefreshKey(state: PagingState<Int, DetailedMovieEntity>): Int? {
            TODO()

        }

        override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, DetailedMovieEntity>> {
            val position = params.key ?: 1
            return tmdbService.searchMovieByQuery(
                api_key = Credentials.API_KEY,
                language = "pt-BR",
                query = query
            )
                .subscribeOn(Schedulers.io())
                .map { toLoadResult(it, position) }
                .onErrorReturn { LoadResult.Error(it)
                }
        }

        private fun toLoadResult(data: AllMoviesResponse, position: Int): LoadResult<Int, DetailedMovieEntity> {
            val mapper = MoviesMapper(moviesDao)
            var newData = mapper.transformIntoBasic(data)
            return LoadResult.Page(
                data = newData.moviesListEntity,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == data.total) null else position + 1
            )
        }
    }