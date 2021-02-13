package by.deniotokiari.ezmap.di.feature

import by.deniotokiari.feature.app.controls.AppControlsFragment
import by.deniotokiari.feature.app.controls.MenuBottomSheetFragment
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val appControlsModule = module {
    factory<AppControlsFragment>()
    factory<MenuBottomSheetFragment>()
}