package by.deniotokiari.ezmap

import android.app.Application
import by.deniotokiari.ezmap.di.featuresModule
import by.deniotokiari.ezmap.di.main
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinExperimentalAPI
import org.koin.core.context.startKoin

@Suppress("unused")
class EzMapApplication : Application() {

    @KoinExperimentalAPI
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@EzMapApplication)
            fragmentFactory()
            modules(
                main,
                *featuresModule
            )
        }
    }
}