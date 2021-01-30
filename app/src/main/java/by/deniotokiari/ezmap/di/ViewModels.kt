package by.deniotokiari.ezmap.di

import by.deniotokiari.ezmap.MainActivityViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel<MainActivityViewModel>()
}