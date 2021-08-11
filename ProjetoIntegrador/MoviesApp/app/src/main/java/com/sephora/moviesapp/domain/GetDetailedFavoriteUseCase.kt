package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.LocalRepositoryImp

class GetDetailedFavoriteUseCase (private val localRepository: LocalRepositoryImp,
                                  private val movieId : Int
) {
    fun execute() = localRepository.getFavorite(movieId)
}