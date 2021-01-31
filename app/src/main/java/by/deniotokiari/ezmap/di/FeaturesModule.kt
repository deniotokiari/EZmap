package by.deniotokiari.ezmap.di

import by.deniotokiari.ezmap.di.feature.mapFeatureModule
import by.deniotokiari.ezmap.di.feature.permissionsHandlerFeatureModule

val featuresModule = arrayOf(
    mapFeatureModule,
    permissionsHandlerFeatureModule
)