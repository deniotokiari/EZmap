package by.deniotokiari.ezmap.di

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import androidx.lifecycle.LiveData
import by.deniotokiari.core.navigation.MainNavigation
import by.deniotokiari.core.tiles.provider.FilesViewModel
import by.deniotokiari.core.tiles.provider.MapsForgeTilesProvider
import by.deniotokiari.core.tiles.provider.TilesProvider
import by.deniotokiari.ezmap.MainActivityViewModel
import by.deniotokiari.utils.android.LocationLiveData
import org.koin.dsl.module
import org.koin.experimental.builder.single
import org.koin.experimental.builder.singleBy
import java.io.File

val main = module {
    factory { get<Context>().resources }
    factory<SharedPreferences> { get<Context>().getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE) }
    factory<TilesProvider<File>> { MapsForgeTilesProvider(get()) }

    singleBy<MainNavigation, MainActivityViewModel>()
    singleBy<LiveData<Location>, LocationLiveData>()

    single<FilesViewModel>()
}