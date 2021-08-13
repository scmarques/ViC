package com.sephora.moviesapp.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import com.sephora.moviesapp.data.model.*
import com.sephora.moviesapp.data.repository.LocalRepositoryImp
import com.sephora.moviesapp.data.repository.RemoteRepositoryImp
import com.sephora.moviesapp.domain.DeleteFavoriteUseCase
import com.sephora.moviesapp.domain.GetGenreListUseCase
import com.sephora.moviesapp.domain.TreatEntityUseCase
import com.sephora.moviesapp.utils.checkNetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val repository: RemoteRepositoryImp,
    private val localRepository: LocalRepositoryImp,
) : ViewModel() {

    private var _genresList = MutableLiveData<List<GenreListEntity.GenreEntity>>()
    val genreList: LiveData<List<GenreListEntity.GenreEntity>> = _genresList
    private val getGenreListUseCase = GetGenreListUseCase(localRepository)
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    private val deleteFavoriteUseCase = DeleteFavoriteUseCase(localRepository)
    private val treatEntityUseCase = TreatEntityUseCase(localRepository)
    private var _errorFound = MutableLiveData<Boolean>(false)
    val errorFound: LiveData<Boolean> = _errorFound
    private val currentGenre = MutableLiveData(DEFAULT_GENRE_MODEL)


    val foundMovies = currentQuery.switchMap { queryString ->
        repository.fetchSearchByQuery(queryString)
            .map { it -> it.filter { it.allGenres.contains(currentGenre.value.toString()) } }
            .doOnError { _errorFound.postValue(true) }
            .cachedIn(viewModelScope).toLiveData()
    }

    val foundFavorite = currentQuery.switchMap { queryString ->
        localRepository.searchInFavorites(queryString)
            .map { it -> it.filter { it.allGenres.contains(currentGenre.value.toString()) } }
            .doOnError { _errorFound.postValue(true) }
            .cachedIn(viewModelScope).toLiveData()
    }

    fun filterByGenre(genreId: Int, query: CharSequence) {
        if (genreId == -1) currentGenre.value = DEFAULT_GENRE_MODEL
        else currentGenre.value = genreId.toString()
        currentQuery.value = query.toString()
    }

    fun getGenresList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = getGenreListUseCase.execute()
                _genresList.postValue(response)
                _errorFound.postValue(false)
            } catch (e: Exception) {
                _errorFound.postValue(true)
            }

        }
    }

    fun changeFavoriteStatus(movieId: Int, isFavorite: Boolean) {
        when (isFavorite) {
            true -> {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        deleteFavoriteUseCase.execute(movieId)
                    } catch (e: Exception) {
                        _errorFound.postValue(true)
                    }
                }
            }
            false -> {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        treatEntityUseCase.execute(movieId = movieId)
                    } catch (e: Exception) {
                        _errorFound.postValue(true)
                    }
                }
            }
        }
    }

    fun checkNetWorkStatus(context: Context): Boolean {
        var isConnected = true
        if (checkNetworkStatus(context)) {
            isConnected = false
            _errorFound.postValue(true)
        }
        return isConnected
    }

    companion object {
        private const val DEFAULT_QUERY = ""
        private const val DEFAULT_GENRE_MODEL = ""
    }

}
