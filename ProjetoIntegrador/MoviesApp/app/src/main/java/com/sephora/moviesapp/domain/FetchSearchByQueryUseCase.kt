package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.RemoteRepositoryImp

class FetchSearchByQueryUseCase(private val query : String,
                                private val repository: RemoteRepositoryImp){
    fun execute() = repository.fetchSearchByQuery(query)
}