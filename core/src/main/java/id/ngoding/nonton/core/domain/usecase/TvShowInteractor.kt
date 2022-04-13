package id.ngoding.nonton.core.domain.usecase

import id.ngoding.nonton.core.data.Resource
import id.ngoding.nonton.core.domain.model.TvShow
import id.ngoding.nonton.core.domain.repository.ITvShowRepository
import kotlinx.coroutines.flow.Flow

class TvShowInteractor(private val iTvShowRepository: ITvShowRepository) : TvShowUseCase {
    override fun getAllTvShow(): Flow<Resource<List<TvShow>>> =
        iTvShowRepository.getAllTvShow()

    override fun getAllFavoriteTvShow(): Flow<List<TvShow>> =
        iTvShowRepository.getAllFavoriteTvShow()

    override fun getDetailTvShowById(id: Int): Flow<TvShow> =
        iTvShowRepository.getDetailTvShowById(id)

    override fun setFavoriteTvShow(tvShow: TvShow) =
        iTvShowRepository.setFavoriteTvShow(tvShow)
}