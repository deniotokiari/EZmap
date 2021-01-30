package by.deniotokiari.ezmap.di

import android.content.Context
import by.deniotokiari.ezmap.MainActivityViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val main = module {
    single { get<Context>().resources }
    viewModel<MainActivityViewModel>()
}