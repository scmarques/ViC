package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.RemoteRepositoryImp

class FetchParentalGuidanceUseCase (private val movieId : Int,
                                    private val repository: RemoteRepositoryImp)
{
    fun execute() = repository.fetchParentalsGuidanceResponse(movieId)
}
