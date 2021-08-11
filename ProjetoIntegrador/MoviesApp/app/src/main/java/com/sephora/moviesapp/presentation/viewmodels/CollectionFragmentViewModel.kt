package com.sephora.moviesapp.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.rxjava2.cachedIn
import com.sephora.moviesapp.data.model.GenreListEntity
import com.sephora.moviesapp.data.repository.LocalRepositoryImp
import com.sephora.moviesapp.data.repository.RemoteRepositoryImp
import com.sephora.moviesapp.domain.*
import com.sephora.moviesapp.utils.Functions
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CollectionFragmentViewModel@Inject constructor(
    private val repository: RemoteRepositoryImp,
    private val localRepository: LocalRepositoryImp
) : ViewModel(){

    private var _genresList = MutableLiveData<List<GenreListEntity.GenreEntity>>()
    val genreList: LiveData<List<GenreListEntity.GenreEntity>> = _genresList
    private val currentQuery = MutableLiveData(DEFAULT_GENRE)
    private val compositeDisposable = CompositeDisposable()
    private val fetchAllMoviesUseCase = FetchAllMoviesUseCase(repository)
    private val treatEntityUseCase = TreatEntityUseCase(localRepository)
    private val getAllFavoritesUseCase = GetAllFavoriteMoviesUseCase(localRepository)
    private val getGenreListUseCase = GetGenreListUseCase(localRepository)
    private val deleteFavoriteUseCase = DeleteFavoriteUseCase(localRepository)
    private val _errorFound = MutableLiveData(DEFAULT_ERROR_STATE)
    private val fetchGetGenreListUseCase = FetchGenresListUseCase (repository)

    val errorFound : LiveData<Boolean> = _errorFound

    val moviesList =  currentQuery.switchMap { queryString ->
        fetchAllMoviesUseCase.execute(queryString)
            .doOnError { _errorFound.postValue(true) }
            .cachedIn(viewModelScope).toLiveData()
    }

    val allFavoriteMovies = currentQuery.switchMap { queryString ->
        getAllFavoritesUseCase.execute(queryString)
            .doOnError { _errorFound.postValue(true) }
            .cachedIn(viewModelScope).toLiveData()
    }

    fun updateGenresList(isConnected : Boolean) {
        if (isConnected) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    localRepository.treatGenreEntity()
                } catch (e: Exception) {
                    _errorFound.postValue(true)
                }
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

    fun filterByGenre(query: CharSequence?) {
        currentQuery.value = query.toString()
    }

    fun getAllFavorites(genreId: String) {
        currentQuery.value = genreId
    }

    fun changeFavoriteStatus(movieId: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                deleteFavoriteUseCase.execute(movieId)
            } catch (e: Exception) {
                _errorFound.postValue(true)
            }
        }
    }

    fun getGenreList(remote : Boolean) {
        if (remote) {
            compositeDisposable.addAll(
                fetchGetGenreListUseCase.execute()
                    .subscribeOn(Schedulers.io())
                    .subscribe({ _genresList.postValue(it) },
                        { _errorFound.postValue(true) })
            )
        } else {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = getGenreListUseCase.execute()
                    _genresList.postValue(response)

                } catch (e: Exception) {
                    _errorFound.postValue(true)
                }
            }
        }
    }

    companion object {
        const val DEFAULT_GENRE = ""
        const val DEFAULT_ERROR_STATE = false
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.isDisposed
    }

    fun checkNetWorkStatus(context: Context): Boolean {
        var isConnected = true
        val status = Functions()
        if (!status.checkNetworkStatus(context)) {
            isConnected = false
            _errorFound.postValue(true)
        } else _errorFound.postValue(false)
        return isConnected
    }

}