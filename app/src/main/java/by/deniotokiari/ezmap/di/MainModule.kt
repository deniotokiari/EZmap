package by.deniotokiari.ezmap.di

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import by.deniotokiari.core.navigation.MainNavigation
import by.deniotokiari.ezmap.MainActivityViewModel
import by.deniotokiari.utils.android.LocationLiveData
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val main = module {
    single { get<Context>().resources }
    singleBy<MainNavigation, MainActivityViewModel>()
    singleBy<LiveData<Location>, LocationLiveData>()
}