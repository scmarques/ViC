package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.RemoteRepositoryImp
import javax.inject.Inject

class FetchDetailedMovieUseCase @Inject constructor(private val repository: RemoteRepositoryImp){
        fun execute( movieId : Int) = repository.fetchDetailedMovieResponse(movieId)
}