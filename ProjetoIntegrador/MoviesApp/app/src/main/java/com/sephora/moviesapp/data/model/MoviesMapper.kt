package com.sephora.moviesapp.data.model

import com.sephora.moviesapp.data.database.MoviesDao
import javax.inject.Inject

class MoviesMapper @Inject constructor (private val moviesDao: MoviesDao) {

    fun transformDetailed(
        detailedMovieModel: DetailedMovieModel): DetailedMovieEntity {
        return DetailedMovieEntity(
            isFavorite = checkInDatabase(movieId = detailedMovieModel.movieId),
            posterPath = detailedMovieModel.posterPath?.let{ path -> Image(path) },
            title = detailedMovieModel.title,
            runtime = detailedMovieModel.runtimeMask(),
            genreResults = detailedMovieModel.genreResults,
            voteAverage = detailedMovieModel.getVoteAverageString(),
            movieOverview = detailedMovieModel.movieOverview,
            movieId = detailedMovieModel.movieId,
            backdropPath = detailedMovieModel.backdropPath?.let{ path -> Image(path)},
            releaseYear = detailedMovieModel.getReleaseYear()
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
            releaseYear = detailedMovieEntity.releaseYear
        )
    }

    fun transformParentalGuidance(movieId: Int, realaseDatesResponse: ReleaseDatesResponse,
    ): ParentalGuidanceEntity {

        return ParentalGuidanceEntity(
           id = 0,
           movieId = movieId,
           parentalGuidance = realaseDatesResponse.toString()
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
    ): GenreListEntity {

        return with(genreResponse) {

            GenreListEntity(
                genreResults.map {
                    GenreListEntity.GenreEntity(
                        id = 0,
                        genreName = it.name,
                        genreId = it.genreId
                    )
                })
        }
    }


   /* fun transform(response: AllMoviesResponse): AllMoviesResponse {
        return with(response) {

            AllMoviesResponse(
                pages = pages,
                page = page,
                movies = movies.map {
                    MovieModel(
                        isFavorite = checkInDatabase(it.movieId),
                        title = it.title,
                        posterPath = it.posterPath,
                        voteAverage = it.voteAverage,
                        movieId = it.movieId,
                        genreId = it.genreId,
                        // it.posterPath?.let { path -> Image(path) },

                    )
                }
            )

        }
    }
*/
/*
     fun transform(response: AllMoviesResponse): AllMoviesList {
        return with(response) {

            AllMoviesList(
                total = total,
                page = page,
                moviesList = movies.map{
                    AllMoviesList.Movie(
                        isFavorite = checkInDatabase(it.movieId),
                        title = it.title,
                        posterPath = it.posterPath?.let { path -> Image(path) },
                        voteAverage = it.getVoteAverageString(),
                        movieId = it.movieId,
                        genreId = it.genreId
                    )
                }
            )

        }
    }*/

    fun transformIntoBasic(allMoviesResponse: AllMoviesResponse): MoviesList {

        return with(allMoviesResponse){
            MoviesList(
                page = this.page,
                pages = this.total,
                movies.map {
                DetailedMovieEntity(
                    isFavorite = checkInDatabase(it.movieId),
                    posterPath = it.posterPath?.let { path -> Image(path) },
                    title = it.title,
                    runtime = "",
                    genreResults = null,
                    voteAverage = it.getVoteAverageString(),
                    movieOverview = null,
                    movieId = it.movieId,
                    backdropPath = null,
                    releaseYear = null
                )
            })
        }


    }


    fun checkInDatabase(movieId: Int): Int {
        var isFavorite = false

        moviesDao.isFavorite(movieId)
               .map {
                   isFavorite = true
               }
        return if (isFavorite) 1 else 0
    }
}