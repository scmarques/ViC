package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.LocalRepositoryImp

class GetGenreListUseCase (private val localRepository: LocalRepositoryImp
) {
    fun execute() = localRepository.getGenreList()
}