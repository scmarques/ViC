package com.sephora.moviesapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sephora.moviesapp.data.model.*
import com.sephora.moviesapp.data.repository.LocalRepositoryImp
import com.sephora.moviesapp.data.repository.RemoteRepositoryImp
import com.sephora.moviesapp.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: RemoteRepositoryImp,
    private val localRepository: LocalRepositoryImp) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var _movieDetailed = MutableLiveData<DetailedMovieEntity>()
    val movieDetailed: LiveData<DetailedMovieEntity> = _movieDetailed
    private var _parentalGuidance = MutableLiveData<ParentalGuidanceEntity>()
    val parentalGuidance: LiveData<ParentalGuidanceEntity> = _parentalGuidance
    private var _movieCredits = MutableLiveData<List<MovieCreditsEntity.CastEntity>>()
    val movieCredits: LiveData<List<MovieCreditsEntity.CastEntity>> = _movieCredits
    private val fetchDetailedMovieUseCase = FetchDetailedMovieUseCase(repository = repository)
    private val deleteFavoriteUseCase = DeleteFavoriteUseCase(localRepository)
    private val _errorFound = MutableLiveData(CollectionFragmentViewModel.DEFAULT_ERROR_STATE)
    val errorFound : LiveData<Boolean> = _errorFound

    fun getMovieDetails(movieId: Int, local: String) {
        when (local) {
            "local" -> {
                val getDetailedFavoriteUseCase = GetDetailedFavoriteUseCase(
                    movieId = movieId, localRepository = localRepository)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = getDetailedFavoriteUseCase.execute()
                        _movieDetailed.postValue(response)

                    } catch (e: Exception) {
                        _errorFound.postValue(true)
                    }
                }
            }
            "remote" -> {
                compositeDisposable.addAll(
                    fetchDetailedMovieUseCase.execute(movieId)
                        .subscribeOn(Schedulers.io())
                        .subscribe ({ _movieDetailed.postValue(it) },
                            { _errorFound.postValue(true)})
                )}
        }
    }

    fun getParentalGuidance(movieId: Int, local: String) {
        when (local) {
            "local" -> {
                val getParentalGuidanceUseCase = GetParentalGuidanceUseCase(
                    movieId = movieId, localRepository = localRepository)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = getParentalGuidanceUseCase.execute()
                        _parentalGuidance.postValue(response)
                    } catch (e: Exception) {
                        _errorFound.postValue(true)
                    }
                }
            }
            "remote" -> {
                val fetchParentalGuidanceUseCase = FetchParentalGuidanceUseCase(
                    movieId = movieId, repository = repository)

                compositeDisposable.addAll(
                    fetchParentalGuidanceUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .subscribe ({ _parentalGuidance.postValue(it) },
                            { _errorFound.postValue(true)})
                )
            }
        }
    }

    fun getMovieCredits(movieId: Int, local: String) {

        when (local) {
            "local" -> {
                val fetchLocalMovieCreditsUseCase = GetLocalMovieCreditsUseCase(
                    movieId = movieId, localRepository = localRepository
                )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = fetchLocalMovieCreditsUseCase.execute()
                        _movieCredits.postValue(response)
                    } catch (e: Exception) {
                        _errorFound.postValue(true)
                    }
                }
            }
            "remote" -> {
                val fetchMovieCreditsUseCase = FetchMovieCreditsUseCase(
                    movieId = movieId,
                    repository = repository
                )
                compositeDisposable.addAll(
                    fetchMovieCreditsUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .subscribe ({ _movieCredits.postValue(it.cast) },
                            { _errorFound.postValue(true)}))
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
                            localRepository.treatEntity(movieId = movieId)
                        } catch (e: Exception) {
                            _errorFound.postValue(true)
                        }
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.isDisposed
    }
}