package id.ngoding.nonton.core.data.remote.api

import id.ngoding.nonton.core.data.remote.response.ListMoviesResponse
import id.ngoding.nonton.core.data.remote.response.ListTvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("3/discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): ListMoviesResponse

    @GET("3/discover/tv")
    suspend fun getTvShows(
        @Query("api_key") apiKey: String
    ): ListTvShowsResponse
}