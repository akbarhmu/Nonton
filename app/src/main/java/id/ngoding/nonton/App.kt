@file:Suppress("unused")

package id.ngoding.nonton

import android.app.Application
import id.ngoding.nonton.core.di.appModule
import id.ngoding.nonton.core.di.networkModule
import id.ngoding.nonton.core.di.repositoryModule
import id.ngoding.nonton.di.useCaseModule
import id.ngoding.nonton.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            androidLogger(Level.NONE)
            modules(arrayListOf(appModule, networkModule, repositoryModule, useCaseModule, viewModelModule))
        }
    }
}