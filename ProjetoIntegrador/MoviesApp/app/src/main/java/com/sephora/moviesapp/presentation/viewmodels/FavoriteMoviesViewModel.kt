package com.sephora.moviesapp.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.sephora.moviesapp.data.model.DetailedMovieEntity
import com.sephora.moviesapp.data.model.GenreListEntity
import com.sephora.moviesapp.data.repository.LocalRepositoryImp
import com.sephora.moviesapp.domain.usecases.DeleteFavoriteUseCase
import com.sephora.moviesapp.domain.usecases.GetAllFavoriteMoviesUseCase
import com.sephora.moviesapp.domain.usecases.GetGenreListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel
@Inject constructor(
    private val localRepository: LocalRepositoryImp,
) : ViewModel() {

    private var _allFavoriteMovies = MutableLiveData<PagingData<DetailedMovieEntity>>()
    val allFavoriteMovies: LiveData<PagingData<DetailedMovieEntity>> = _allFavoriteMovies
    private var _genresList = MutableLiveData<List<GenreListEntity.GenreEntity>>()
    val genresList: LiveData<List<GenreListEntity.GenreEntity>> = _genresList
    private val getAllFavoritesUseCase = GetAllFavoriteMoviesUseCase(localRepository)
    private val getGenreListUseCase = GetGenreListUseCase(localRepository)

    @ExperimentalCoroutinesApi
    fun getAllFavorites() {
        CoroutineScope(Dispatchers.IO).launch {
            getAllFavoritesUseCase.execute()
                .cachedIn(viewModelScope)
                .subscribe { response ->
                    _allFavoriteMovies.postValue(response)
                }
        }
    }

    fun changeFavoriteStatus(movieId: Int) {
        val deleteFavoriteUseCase = DeleteFavoriteUseCase(localRepository, movieId = movieId)
        CoroutineScope(Dispatchers.IO).launch {
            deleteFavoriteUseCase.execute()
        }
    }

    fun getGenreList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = getGenreListUseCase.execute()
            _genresList.postValue(response)
        }
    }

}