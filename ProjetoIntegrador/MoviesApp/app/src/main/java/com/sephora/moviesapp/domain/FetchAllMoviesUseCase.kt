package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.RemoteRepositoryImp
import javax.inject.Inject

class FetchAllMoviesUseCase@Inject constructor(private val repository: RemoteRepositoryImp) {

    fun execute( query : String) = repository.fetchAllMoviesResponse(query)
}