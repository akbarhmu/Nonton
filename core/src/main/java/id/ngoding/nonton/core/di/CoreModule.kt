package id.ngoding.nonton.core.di

import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import id.ngoding.nonton.core.BuildConfig
import id.ngoding.nonton.core.data.local.LocalDataSource
import id.ngoding.nonton.core.data.local.room.CatalogDatabase
import id.ngoding.nonton.core.data.remote.RemoteDataSource
import id.ngoding.nonton.core.data.remote.api.ApiService
import id.ngoding.nonton.core.data.repository.MovieRepository
import id.ngoding.nonton.core.data.repository.TvShowRepository
import id.ngoding.nonton.core.domain.repository.IMovieRepository
import id.ngoding.nonton.core.domain.repository.ITvShowRepository
import id.ngoding.nonton.core.util.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val repositoryModule = module {

    single { LocalDataSource(get(), get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> { MovieRepository(get(), get(), get()) }
    single<ITvShowRepository> { TvShowRepository(get(), get(), get()) }

}

val appModule = module {
    single { GsonBuilder().setLenient().create() }
    single {
        Room.databaseBuilder(androidContext(), CatalogDatabase::class.java, "catalog-movies-db")
            .build()
    }
    factory { get<CatalogDatabase>().movieDao() }
    factory { get<CatalogDatabase>().tvShowDao() }
}

val networkModule = module {

    single {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get() as Gson))
            .client(get() as OkHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}