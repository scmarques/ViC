package com.sephora.moviesapp.domain.usecases

import com.sephora.moviesapp.data.repository.RemoteRepositoryImp

class FetchMoviesByGenreUseCase(private val genreId : Int,
                                private val repository: RemoteRepositoryImp
)
{
//    fun execute() = repository.fetchMoviesByGenreResponse(genreId)
}
