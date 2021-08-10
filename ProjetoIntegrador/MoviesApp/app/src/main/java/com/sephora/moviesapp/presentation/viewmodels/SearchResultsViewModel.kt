package com.sephora.moviesapp.presentation.viewmodels

import androidx.lifecycle.*

import androidx.paging.rxjava2.cachedIn
import com.sephora.moviesapp.data.api.TmdbService
import com.sephora.moviesapp.data.database.MoviesDao
import com.sephora.moviesapp.data.model.*
import com.sephora.moviesapp.data.repository.LocalRepositoryImp
import com.sephora.moviesapp.data.repository.RemoteRepositoryImp
import com.sephora.moviesapp.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val repository: RemoteRepositoryImp,
    private val localRepository: LocalRepositoryImp,
    private val moviesDao: MoviesDao
) : ViewModel(){

    private val mapper = MoviesMapper(moviesDao = moviesDao)
    private var _genresList = MutableLiveData<GenreListEntity>()
    val genreList : LiveData<GenreListEntity> = _genresList
    private val fetchGenreResponse = FetchGenresListUseCase(repository)
    private val compositeDisposable = CompositeDisposable()
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)


        val foundMovies = currentQuery.switchMap { queryString ->
            repository.fetchSearchByQuery(queryString)
                .cachedIn(viewModelScope).toLiveData()
    }

    val foundFavorite = currentQuery.switchMap { queryString ->
        localRepository.searchInFavorites(queryString)
            .cachedIn(viewModelScope).toLiveData()
    }

    fun searchMovie(query: CharSequence) {
        currentQuery.value = query.toString()
    }

    fun getGenresList() {

        compositeDisposable.addAll(
            fetchGenreResponse.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { mapper.transformGenreList(it) }
                .subscribe { _genresList.postValue(it) }
        )
    }


    companion object {
        private const val DEFAULT_QUERY = ""
    }
    }

