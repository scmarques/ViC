package com.sephora.moviesapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sephora.moviesapp.data.database.MoviesDao
import com.sephora.moviesapp.data.model.*
import com.sephora.moviesapp.data.repository.LocalRepositoryImp
import com.sephora.moviesapp.data.repository.RemoteRepositoryImp
import com.sephora.moviesapp.domain.usecases.*
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
    private val localRepository: LocalRepositoryImp,
    private val moviesDao: MoviesDao
) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var _movieDetailed = MutableLiveData<DetailedMovieEntity>()
    val movieDetailed: LiveData<DetailedMovieEntity> = _movieDetailed
    private var _parentalGuidance = MutableLiveData<ParentalGuidanceEntity>()
    val parentalGuidance: LiveData<ParentalGuidanceEntity> = _parentalGuidance
    private var _movieCredits = MutableLiveData<List<MovieCreditsEntity.CastEntity>>()
    val movieCredits: LiveData<List<MovieCreditsEntity.CastEntity>> = _movieCredits


    private val mapper = MoviesMapper(moviesDao = moviesDao)


    fun getMovieDetails(movieId: Int, local: String) {
        when (local) {
            "local" -> {
                val fetchDetailedFavoriteUseCase = GetDetailedFavoriteUseCase(
                    movieId = movieId, localRepository = localRepository
                )
                CoroutineScope(Dispatchers.IO).launch {
                    val response = fetchDetailedFavoriteUseCase.execute()
                    _movieDetailed.postValue(response)
                }


            }
            "remote" -> {
                val fetchDetailedMovieUseCase = FetchDetailedMovieUseCase(
                    movieId = movieId,
                    repository = repository
                )

                compositeDisposable.addAll(
                    fetchDetailedMovieUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .map { mapper.transformDetailed(it) }
                        .subscribe { _movieDetailed.postValue(it) }
                )
            }
        }

    }

    fun getParentalGuidance(movieId: Int, local: String) {
        when (local) {
            "local" -> {
                val getParentalGuidanceUseCase = GetParentalGuidanceUseCase(
                    movieId = movieId,
                    localRepository = localRepository
                )
                CoroutineScope(Dispatchers.IO).launch {
                    val response = getParentalGuidanceUseCase.execute()
                    _parentalGuidance.postValue(response)
                }

            }
            "remote" -> {
                val fetchParentalGuidanceUseCase = FetchParentalGuidanceUseCase(
                    movieId = movieId, repository = repository
                )

                compositeDisposable.addAll(
                    fetchParentalGuidanceUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .map { mapper.transformParentalGuidance(movieId, it) }
                        .subscribe { _parentalGuidance.postValue(it) }
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
                    val response = fetchLocalMovieCreditsUseCase.execute()
                    _movieCredits.postValue(response)
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
                        .map { mapper.transformCast(movieId, it) }
                        .subscribe { _movieCredits.postValue(it.cast) }
                )
            }
        }

    }

    fun changeFavoriteStatus(movieId: Int, isFavorite: Boolean) {
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.isDisposed
    }
}
