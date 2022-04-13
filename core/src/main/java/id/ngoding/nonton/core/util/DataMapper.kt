package id.ngoding.nonton.core.util

import id.ngoding.nonton.core.data.local.entity.MovieEntity
import id.ngoding.nonton.core.data.local.entity.TvShowEntity
import id.ngoding.nonton.core.data.remote.response.MovieResponse
import id.ngoding.nonton.core.data.remote.response.TvShowResponse
import id.ngoding.nonton.core.domain.model.Movie
import id.ngoding.nonton.core.domain.model.TvShow

object DataMapper {

    fun mapListMoviesResponseToListMovieEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                it.id,
                it.overview,
                it.title,
                it.posterPath,
                it.backdropPath,
                it.releaseDate,
                it.voteAverage,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapListTvShowsResponseToListTvShowEntities(input: List<TvShowResponse>): List<TvShowEntity> {
        val tvShowList = ArrayList<TvShowEntity>()
        input.map {
            val tvShow = TvShowEntity(
                it.id,
                it.firstAirDate,
                it.overview,
                it.posterPath,
                it.backdropPath,
                it.voteAverage,
                it.name,
                isFavorite = false
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapMovieEntityToMovieDomain(input: MovieEntity): Movie {
        return Movie(
            input.id,
            input.overview,
            input.title,
            input.posterPath,
            input.backdropPath,
            input.releaseDate,
            input.voteAverage,
            input.isFavorite
        )
    }

    fun mapTvShowEntityToTvShowDomain(input: TvShowEntity): TvShow {
        return TvShow(
            input.id,
            input.firstAirDate,
            input.overview,
            input.posterPath,
            input.backdropPath,
            input.voteAverage,
            input.name,
            input.isFavorite
        )
    }

    fun mapListMovieEntitiesToListMovieDomain(input: List<MovieEntity>): List<Movie> {
        return input.map {
            Movie(
                it.id,
                it.overview,
                it.title,
                it.posterPath,
                it.backdropPath,
                it.releaseDate,
                it.voteAverage,
                it.isFavorite
            )
        }
    }

    fun mapListTvShowEntitiesToListTvShowDomain(input: List<TvShowEntity>): List<TvShow> {
        return input.map {
            TvShow(
                it.id,
                it.firstAirDate,
                it.overview,
                it.posterPath,
                it.backdropPath,
                it.voteAverage,
                it.name,
                it.isFavorite
            )
        }
    }

    fun mapMovieDomainToMovieEntity(input: Movie): MovieEntity {
        return MovieEntity(
            input.id,
            input.overview,
            input.title,
            input.posterPath,
            input.backdropPath,
            input.releaseDate,
            input.voteAverage,
            input.isFavorite
        )
    }

    fun mapTvShowDomainToTvShowEntity(input: TvShow): TvShowEntity {
        return TvShowEntity(
            input.id,
            input.firstAirDate,
            input.overview,
            input.posterPath,
            input.backdropPath,
            input.voteAverage,
            input.name,
            input.isFavorite
        )
    }

}