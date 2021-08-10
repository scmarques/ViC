package com.sephora.moviesapp.domain.usecases

import com.sephora.moviesapp.data.repository.LocalRepositoryImp

class GetGenreListUseCase (private val localRepository: LocalRepositoryImp
) {
    fun execute() = localRepository.getGenreList()
}