package com.sephora.moviesapp.domain.usecases

import com.sephora.moviesapp.data.model.GenreListEntity
import com.sephora.moviesapp.data.repository.LocalRepositoryImp

class InsertGenreListUseCase (private val localRepository: LocalRepositoryImp,
                              private val genreList : List<GenreListEntity.GenreEntity>
) {
    fun execute() = localRepository.insertGenreList(genreList)
}