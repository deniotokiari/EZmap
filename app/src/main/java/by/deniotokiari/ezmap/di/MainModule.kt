package by.deniotokiari.ezmap.di

import android.content.Context
import by.deniotokiari.core.navigation.MainNavigation
import by.deniotokiari.ezmap.MainActivityViewModel
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.TypeQualifier
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val main = module {
    single { get<Context>().resources }
    singleBy<MainNavigation, MainActivityViewModel>()
}