package id.ngoding.nonton.home.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.ngoding.nonton.core.domain.usecase.MovieUseCase

class MovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getMovies() = movieUseCase.getAllMovies().asLiveData()
}