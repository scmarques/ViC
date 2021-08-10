package com.sephora.moviesapp.presentation.viewmodels

import androidx.lifecycle.*

import androidx.paging.*
import androidx.paging.rxjava2.cachedIn
import com.sephora.moviesapp.data.database.MoviesDao
import com.sephora.moviesapp.data.api.TmdbService
import com.sephora.moviesapp.data.model.*
import com.sephora.moviesapp.data.repository.LocalRepositoryImp
import com.sephora.moviesapp.data.repository.RemoteRepositoryImp
import com.sephora.moviesapp.domain.usecases.*
import com.sephora.moviesapp.data.model.MoviesMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class SharedScreenViewModel @Inject constructor(private val repository: RemoteRepositoryImp,
                                                private val localRepository : LocalRepositoryImp,
                                                private val moviesDao: MoviesDao,
                                                private val tmdbService: TmdbService) :
    ViewModel() {

    private val mapper = MoviesMapper(moviesDao = moviesDao)
    private var _genresList = MutableLiveData<List<GenreListEntity.GenreEntity>>()
    val genreList : LiveData<List<GenreListEntity.GenreEntity>> = _genresList
    private val currentQuery = MutableLiveData(DEFAULT_GENRE)
    private val compositeDisposable = CompositeDisposable()
    private val fetchGenreResponse = FetchGenresListUseCase(repository)
    private val getGenreListUseCase = GetGenreListUseCase(localRepository)


    val moviesList = currentQuery.switchMap { queryString ->
        repository.fetchAllMoviesResponse(queryString)
            .cachedIn(viewModelScope).toLiveData()
    }
/*
        fun getMoviesByGenre(selectedGenre: GenreListEntity.GenreEntity?) {

            CoroutineScope(Dispatchers.IO).launch {
                if (selectedGenre != null) {
                    fetchAllMoviesUseCase.execute()
                        .cachedIn(viewModelScope)
                //        .filter { paging -> paging.filter { it.genreId.contains(selectedGenre.genreId) } }
                       // .map{ pagingData -> pagingData.filter {it.genreResults?.let{ == selectedGenre.genreId) } }}
                        .subscribe {response ->
                            _moviesLiveData.postValue(response) }
                } else {
                    fetchAllMoviesUseCase.execute()
                        .cachedIn(viewModelScope)
                        .subscribe {response ->
                            _moviesLiveData.postValue(response) }
                }
            }
    }
    */


    fun getGenresList(isConnected : Boolean) {

        when (isConnected){
            true -> {
                compositeDisposable.addAll(
                    fetchGenreResponse.execute()
                        .subscribeOn(Schedulers.io())
                        .map { mapper.transformGenreList(it) }
                        .subscribe { _genresList.postValue(it.genreResults) }
                )
            }

            false -> {
                CoroutineScope(Dispatchers.IO).launch {
                       val response = getGenreListUseCase.execute()
                        _genresList.postValue(response)
                }
            }
        }

    }


    fun changeFavoriteStatus(movieId : Int, isFavorite : Boolean) {
        when (isFavorite) {
            true -> {
                val deleteFavoriteUseCase = DeleteFavoriteUseCase(localRepository, movieId)
                CoroutineScope(Dispatchers.IO).launch {
                    deleteFavoriteUseCase.execute()
                }
            }
            false -> {
                localRepository.treatEntity(movieId = movieId)
            }
        }
    }


    fun filterByGenre(query: CharSequence?) {
        currentQuery.value = query.toString()
    }

    companion object {
        private const val DEFAULT_GENRE = ""
    }

    }





