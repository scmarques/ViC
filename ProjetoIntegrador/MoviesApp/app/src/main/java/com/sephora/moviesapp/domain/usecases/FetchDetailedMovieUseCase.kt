package com.sephora.moviesapp.domain.usecases

import com.sephora.moviesapp.data.repository.RemoteRepositoryImp

class FetchDetailedMovieUseCase (private val movieId : Int,
    private val repository: RemoteRepositoryImp){
        fun execute() = repository.fetchDetailedMovieResponse(movieId)
}