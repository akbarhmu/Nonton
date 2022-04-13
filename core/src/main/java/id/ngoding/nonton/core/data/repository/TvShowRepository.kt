package id.ngoding.nonton.core.data.repository

import id.ngoding.nonton.core.data.NetworkBoundResource
import id.ngoding.nonton.core.data.Resource
import id.ngoding.nonton.core.data.local.LocalDataSource
import id.ngoding.nonton.core.data.remote.RemoteDataSource
import id.ngoding.nonton.core.data.remote.api.ApiResponse
import id.ngoding.nonton.core.data.remote.response.TvShowResponse
import id.ngoding.nonton.core.domain.model.TvShow
import id.ngoding.nonton.core.domain.repository.ITvShowRepository
import id.ngoding.nonton.core.util.AppExecutors
import id.ngoding.nonton.core.util.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TvShowRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
) : ITvShowRepository {

    override fun getAllTvShow(): Flow<Resource<List<TvShow>>> =
        object : NetworkBoundResource<List<TvShow>, List<TvShowResponse>>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getAllTvShows().map {
                    DataMapper.mapListTvShowEntitiesToListTvShowDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getTvShows()

            override suspend fun saveCallResult(data: List<TvShowResponse>) {
                val listTvShow = DataMapper.mapListTvShowsResponseToListTvShowEntities(data)
                localDataSource.insertTvShows(listTvShow)
            }

        }.asFlow()

    override fun getAllFavoriteTvShow(): Flow<List<TvShow>> {
        return localDataSource.getAllFavoriteTvShows().map {
            DataMapper.mapListTvShowEntitiesToListTvShowDomain(it)
        }
    }

    override fun getDetailTvShowById(id: Int): Flow<TvShow> {
        return localDataSource.getDetailTvShowById(id).map {
            DataMapper.mapTvShowEntityToTvShowDomain(it)
        }
    }

    override fun setFavoriteTvShow(tvShow: TvShow) {
        val mapper = DataMapper.mapTvShowDomainToTvShowEntity(tvShow)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(mapper) }
    }


}