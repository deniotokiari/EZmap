package by.deniotokiari.ezmap.di.feature

import by.deniotokiari.feature.permissions.PermissionsHandlerFragment
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val permissionsHandlerFeatureModule = module {
    factory<PermissionsHandlerFragment>()
}