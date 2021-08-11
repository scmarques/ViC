package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.RemoteRepositoryImp

class FetchGenresListUseCase (private val repository: RemoteRepositoryImp)
{
    fun execute() = repository.fetchGenresListResponse()
}