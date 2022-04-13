package id.ngoding.nonton.core.data.repository

import id.ngoding.nonton.core.data.NetworkBoundResource
import id.ngoding.nonton.core.data.Resource
import id.ngoding.nonton.core.data.local.LocalDataSource
import id.ngoding.nonton.core.data.remote.RemoteDataSource
import id.ngoding.nonton.core.data.remote.api.ApiResponse
import id.ngoding.nonton.core.data.remote.response.MovieResponse
import id.ngoding.nonton.core.domain.model.Movie
import id.ngoding.nonton.core.domain.repository.IMovieRepository
import id.ngoding.nonton.core.util.AppExecutors
import id.ngoding.nonton.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapListMovieEntitiesToListMovieDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovies()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val listMovie = DataMapper.mapListMoviesResponseToListMovieEntities(data)
                localDataSource.insertMovies(listMovie)
            }
        }.asFlow()

    override fun getAllFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getAllFavoriteMovies().map {
            DataMapper.mapListMovieEntitiesToListMovieDomain(it)
        }
    }

    override fun getDetailMovieById(id: Int): Flow<Movie> {
        return localDataSource.getDetailMovieById(id).map {
            DataMapper.mapMovieEntityToMovieDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie) {
        val mapper = DataMapper.mapMovieDomainToMovieEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(mapper) }
    }

}