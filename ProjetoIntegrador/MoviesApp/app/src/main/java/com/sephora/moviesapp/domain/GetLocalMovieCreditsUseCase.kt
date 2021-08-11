package com.sephora.moviesapp.domain

import com.sephora.moviesapp.data.repository.LocalRepositoryImp

class GetLocalMovieCreditsUseCase (private val movieId : Int,
                                   private val localRepository: LocalRepositoryImp){
        fun execute() = localRepository.getCastList(movieId)
    }
