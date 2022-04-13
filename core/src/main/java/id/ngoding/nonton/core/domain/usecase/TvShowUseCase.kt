package id.ngoding.nonton.core.domain.usecase

import id.ngoding.nonton.core.data.Resource
import id.ngoding.nonton.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowUseCase {

    fun getAllTvShow(): Flow<Resource<List<TvShow>>>

    fun getAllFavoriteTvShow(): Flow<List<TvShow>>

    fun getDetailTvShowById(id: Int): Flow<TvShow>

    fun setFavoriteTvShow(tvShow: TvShow)
}