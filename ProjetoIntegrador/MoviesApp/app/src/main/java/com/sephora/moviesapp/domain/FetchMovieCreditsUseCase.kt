package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.RemoteRepositoryImp

class FetchMovieCreditsUseCase (private val movieId : Int,
                                private val repository: RemoteRepositoryImp
){
    fun execute() = repository.fetchMovieCreditsResponse(movieId)
}