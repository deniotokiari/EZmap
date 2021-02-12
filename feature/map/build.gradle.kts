plugins {
    id(Plugins.library)
    id(Plugins.common_android)
}

viewBinding = true

dependencies {
    implementation(Dependencies.appcompat)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.corektx)
    implementation(*Dependencies.koin)
    implementation(*Dependencies.lifecycle)
    implementation(Dependencies.osmdroid_maps_forge)
    implementation(Dependencies.maps_forge_android)
    implementation(Dependencies.maps_forge_map)
    implementation(Dependencies.maps_forge_themes)
    implementation(Dependencies.material)
    implementation(Dependencies.osmdroid)

    implementation(":core:design")
    implementation(":core:strings")
    implementation(":utils:android")
    implementation(":utils:kotlin")
}