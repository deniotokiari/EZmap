package by.deniotokiari.ezmap.di.feature

import by.deniotokiari.feature.map.MapFragment
import by.deniotokiari.feature.map.MapViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val mapFeatureModule = module {
    factory<MapFragment>()
    viewModel<MapViewModel>()
}