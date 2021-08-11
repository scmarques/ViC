package com.sephora.moviesapp.data.model

import android.util.Log
import com.sephora.moviesapp.data.database.MoviesDao
import javax.inject.Inject

class MoviesMapper @Inject constructor (private val moviesDao: MoviesDao) {


    fun transformDetailed(
        detailedMovieModel: DetailedMovieModel): DetailedMovieEntity {
        return DetailedMovieEntity(
            isFavorite = checkInDatabase(moviesDao, detailedMovieModel.movieId),
            posterPath = detailedMovieModel.posterPath?.let{ path -> Image(path) },
            title = detailedMovieModel.title,
            runtime = detailedMovieModel.runtimeMask(detailedMovieModel.runtime),
            genreResults = detailedMovieModel.genreResults,
            voteAverage = detailedMovieModel.getVoteAverageString(detailedMovieModel.voteAverage),
            movieOverview = detailedMovieModel.movieOverview,
            movieId = detailedMovieModel.movieId,
            backdropPath = detailedMovieModel.backdropPath?.let{ path -> Image(path)},
            releaseYear = detailedMovieModel.getReleaseYear(detailedMovieModel.releaseDate),
            allGenres = ""
        )
    }

    fun transformFavoriteStatus(
        detailedMovieEntity: DetailedMovieEntity, isFavorite : Int): DetailedMovieEntity {

        return DetailedMovieEntity(
            isFavorite = isFavorite,
            posterPath = detailedMovieEntity.posterPath,
            title = detailedMovieEntity.title,
            runtime = detailedMovieEntity.runtime,
            genreResults = detailedMovieEntity.genreResults,
            voteAverage = detailedMovieEntity.voteAverage,
            movieOverview = detailedMovieEntity.movieOverview,
            movieId = detailedMovieEntity.movieId,
            backdropPath = detailedMovieEntity.backdropPath,
            releaseYear = detailedMovieEntity.releaseYear,
            allGenres = ""
        )
    }

    fun transformParentalGuidance(movieId: Int, releaseDatesResponse: ReleaseDatesResponse,
    ): ParentalGuidanceEntity {

        return ParentalGuidanceEntity(
           id = 0,
           movieId = movieId,
           parentalGuidance = releaseDatesResponse.toString()
        )
    }

    fun transformCast(movieId: Int, movieCreditsResponse: MovieCreditsResponse
    ): MovieCreditsEntity {

        return with (movieCreditsResponse){

            MovieCreditsEntity(
                listId = id,
                cast = castList.map {
                    MovieCreditsEntity.CastEntity(
                        id = 0,
                        nameActor = it.nameActor,
                        movieId = movieId,
                        character = it.character,
                        profile_path = it.profile_path?.let { path -> Image(path) },
                    )
                }
            )
        }
    }

    fun transformGenreList(genreResponse: GenreResponse
    ): List<GenreListEntity.GenreEntity> {

        return with(genreResponse) {

          genreResponse.genreResults.map {
                    GenreListEntity.GenreEntity(
                        genreName = it.name,
                        genreId = it.genreId
                    )
                }
        }
    }

    fun transformGenreModel(genre: GenreListEntity.GenreEntity
    ) : GenreModel {

        return with(genre) {

               GenreModel(
                    name = genreName ,
                    genreId = genreId
                )
            }
        }

    fun transformIntoBasic(allMoviesResponse: AllMoviesResponse): MoviesList {

        return with(allMoviesResponse){
            MoviesList(
                page = this.page,
                pages = this.total,
                movies.map {
                DetailedMovieEntity(
                    isFavorite = checkInDatabase(moviesDao, it.movieId),
                    posterPath = it.posterPath?.let { path -> Image(path) },
                    title = it.title,
                    runtime = "",
                    genreResults = null,
                    voteAverage = it.getVoteAverageString(it.voteAverage),
                    movieOverview = null,
                    movieId = it.movieId,
                    backdropPath = null,
                    releaseYear = null,
                    allGenres = makeGenreString(it.genreId)
                )
            })
        }
    }

    fun checkInDatabase(moviesDao: MoviesDao, movieId: Int): Int {
        var isFavorite = false
        moviesDao.isFavorite(movieId)
               .map {
                   isFavorite = true
               }
        return if (isFavorite) 1 else 0
    }

    fun makeGenreString(genreResult : List<Int>) : String {
        var genres = ""
            for (i in genreResult) {
                genres += "${i}, "
            }
        return genres
    }

}