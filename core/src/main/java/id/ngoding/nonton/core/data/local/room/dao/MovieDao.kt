package id.ngoding.nonton.core.data.local.room.dao

import androidx.room.*
import id.ngoding.nonton.core.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM tb_movie")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tb_movie WHERE id = :id")
    fun getDetailMovieById(id: Int): Flow<MovieEntity>

    @Update
    fun updateMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM tb_movie WHERE isFavorite = 1")
    fun getAllFavoriteMovies(): Flow<List<MovieEntity>>
}