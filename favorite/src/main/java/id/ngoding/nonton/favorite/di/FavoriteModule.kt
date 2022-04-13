package id.ngoding.nonton.favorite.di

import id.ngoding.nonton.favorite.movie.FavoriteMovieViewModel
import id.ngoding.nonton.favorite.tvshow.FavoriteTvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteTvShowViewModel(get()) }
    viewModel { FavoriteMovieViewModel(get()) }
}