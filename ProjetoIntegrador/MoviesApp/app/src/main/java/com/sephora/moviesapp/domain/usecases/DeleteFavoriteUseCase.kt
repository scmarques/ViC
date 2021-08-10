package com.sephora.moviesapp.domain.usecases

import com.sephora.moviesapp.data.repository.LocalRepositoryImp

class DeleteFavoriteUseCase (private val localRepository: LocalRepositoryImp,
                             private val movieId :Int) {
    fun execute() = localRepository.deleteFavorite(movieId = movieId)
}