package id.ngoding.nonton.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ngoding.nonton.core.data.local.entity.MovieEntity
import id.ngoding.nonton.core.data.local.entity.TvShowEntity
import id.ngoding.nonton.core.data.local.room.dao.MovieDao
import id.ngoding.nonton.core.data.local.room.dao.TvShowDao

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 2, exportSchema = false)
abstract class CatalogDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
}