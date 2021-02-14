plugins {
    id(Plugins.library)
    id(Plugins.common_android)
}

viewBinding = true

dependencies {
    implementation(Dependencies.appcompat)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.corektx)
    implementation(Dependencies.fragment)
    implementation(*Dependencies.lifecycle)
    implementation(Dependencies.material)

    implementation(":core:design")
    implementation(":core:navigation")
    implementation(":core:strings")
    implementation(":core:tiles-provider")
    implementation(":utils:android")
    implementation(":utils:kotlin")
}