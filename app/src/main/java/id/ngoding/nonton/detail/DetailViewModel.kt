package id.ngoding.nonton.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.ngoding.nonton.core.domain.model.Movie
import id.ngoding.nonton.core.domain.model.TvShow
import id.ngoding.nonton.core.domain.usecase.MovieUseCase
import id.ngoding.nonton.core.domain.usecase.TvShowUseCase

class DetailViewModel(
    private val movieUseCase: MovieUseCase,
    private val tvShowUseCase: TvShowUseCase
) : ViewModel() {

    fun getDetailMovieById(id: Int) = movieUseCase.getDetailMovieById(id).asLiveData()

    fun getDetailTvShowById(id: Int) = tvShowUseCase.getDetailTvShowById(id).asLiveData()

    fun setFavoriteMovie(movie: Movie) = movieUseCase.setFavoriteMovie(movie)

    fun setFavoriteTvShow(tvShow: TvShow) = tvShowUseCase.setFavoriteTvShow(tvShow)
}