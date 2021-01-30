package by.deniotokiari.ezmap

import android.app.Application
import by.deniotokiari.ezmap.di.application
import by.deniotokiari.ezmap.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class EzMapApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@EzMapApplication)
            modules(
                application,
                viewModels
            )
        }
    }
}