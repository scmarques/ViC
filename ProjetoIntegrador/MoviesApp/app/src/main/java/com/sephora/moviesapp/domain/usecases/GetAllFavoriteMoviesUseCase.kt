package com.sephora.moviesapp.domain.usecases

import com.sephora.moviesapp.data.repository.LocalRepositoryImp

class GetAllFavoriteMoviesUseCase (private val localRepository: LocalRepositoryImp
) {
    fun execute() = localRepository.selectAllFavorite()
}