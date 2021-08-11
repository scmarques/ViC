package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.LocalRepositoryImp

class GetAllFavoriteMoviesUseCase (private val localRepository: LocalRepositoryImp
) {
    fun execute(genreId : String) = localRepository.selectAllFavorite(genreId)
}