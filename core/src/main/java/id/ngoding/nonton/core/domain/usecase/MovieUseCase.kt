package id.ngoding.nonton.core.domain.usecase

import id.ngoding.nonton.core.data.Resource
import id.ngoding.nonton.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {

    fun getAllMovies(): Flow<Resource<List<Movie>>>

    fun getAllFavoriteMovies(): Flow<List<Movie>>

    fun getDetailMovieById(id: Int): Flow<Movie>

    fun setFavoriteMovie(movie: Movie)

}