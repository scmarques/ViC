package com.sephora.moviesapp.domain.usecases

import com.sephora.moviesapp.data.repository.RemoteRepositoryImp

class FetchAllMoviesUseCase(
    private val repository: RemoteRepositoryImp,
    private val query : String
) {
    fun execute() = repository.fetchAllMoviesResponse(query)
}