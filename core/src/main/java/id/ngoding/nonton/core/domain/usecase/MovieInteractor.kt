package id.ngoding.nonton.core.domain.usecase

import id.ngoding.nonton.core.data.Resource
import id.ngoding.nonton.core.domain.model.Movie
import id.ngoding.nonton.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val iMovieRepository: IMovieRepository) : MovieUseCase {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        iMovieRepository.getAllMovies()

    override fun getAllFavoriteMovies(): Flow<List<Movie>> =
        iMovieRepository.getAllFavoriteMovies()

    override fun getDetailMovieById(id: Int): Flow<Movie> =
        iMovieRepository.getDetailMovieById(id)

    override fun setFavoriteMovie(movie: Movie) =
        iMovieRepository.setFavoriteMovie(movie)
}