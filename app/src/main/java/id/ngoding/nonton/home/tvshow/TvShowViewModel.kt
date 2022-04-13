package id.ngoding.nonton.home.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.ngoding.nonton.core.domain.usecase.TvShowUseCase

class TvShowViewModel(private val tvShowUseCase: TvShowUseCase) : ViewModel() {

    fun getTvShow() = tvShowUseCase.getAllTvShow().asLiveData()
}