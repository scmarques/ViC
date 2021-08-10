package com.sephora.moviesapp.domain.usecases

import com.sephora.moviesapp.data.repository.LocalRepositoryImp


class SearchFavoritesUseCase(private val localRepository: LocalRepositoryImp,
    private val query : String) {

        fun execute() = localRepository.searchInFavorites(query)
    }
