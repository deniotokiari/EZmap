package by.deniotokiari.ezmap.di

import android.content.Context
import by.deniotokiari.ezmap.MainActivityViewModel
import by.deniotokiari.utils.kotlin.TestLib
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.single

val main = module {
    single { get<Context>().resources }
    viewModel<MainActivityViewModel>()
}