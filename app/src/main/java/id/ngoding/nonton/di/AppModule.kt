package id.ngoding.nonton.di

import id.ngoding.nonton.core.domain.usecase.MovieInteractor
import id.ngoding.nonton.core.domain.usecase.MovieUseCase
import id.ngoding.nonton.core.domain.usecase.TvShowInteractor
import id.ngoding.nonton.core.domain.usecase.TvShowUseCase
import id.ngoding.nonton.detail.DetailViewModel
import id.ngoding.nonton.home.movie.MovieViewModel
import id.ngoding.nonton.home.tvshow.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
}

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
    factory<TvShowUseCase> { TvShowInteractor(get()) }
}