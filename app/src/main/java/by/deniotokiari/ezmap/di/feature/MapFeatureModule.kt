package by.deniotokiari.ezmap.di.feature

import by.deniotokiari.feature.map.MapFragment
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val mapFeatureModule = module {
    factory<MapFragment>()
}