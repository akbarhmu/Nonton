package id.ngoding.nonton.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.ngoding.nonton.core.domain.usecase.TvShowUseCase

class FavoriteTvShowViewModel(
    private val tvShowUseCase: TvShowUseCase
) : ViewModel() {
    fun getFavoriteTvShow() = tvShowUseCase.getAllFavoriteTvShow().asLiveData()
}