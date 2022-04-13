package id.ngoding.nonton.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.ngoding.nonton.core.domain.usecase.MovieUseCase

class FavoriteMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getFavoriteMovie() = movieUseCase.getAllFavoriteMovies().asLiveData()
}