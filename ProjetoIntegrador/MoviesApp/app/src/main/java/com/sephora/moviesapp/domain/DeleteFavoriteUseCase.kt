package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.LocalRepositoryImp
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(private val localRepository: LocalRepositoryImp) {
    fun execute(movieId :Int) = localRepository.deleteFavorite(movieId = movieId)
}