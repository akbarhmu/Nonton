package id.ngoding.nonton.core.data.local.room.dao

import androidx.room.*
import id.ngoding.nonton.core.data.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tb_tvshow")
    fun getAllTvShow(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tb_tvshow WHERE id = :id")
    fun getDetailTvShowById(id: Int): Flow<TvShowEntity>

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>)

    @Query("SELECT * FROM tb_tvshow WHERE isFavorite = 1")
    fun getAllFavoriteTvShows(): Flow<List<TvShowEntity>>
}