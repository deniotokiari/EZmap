plugins {
    id(Plugins.application)
    id(Plugins.common_android)
}

versionCode = AppConfig.versionCode
versionName = AppConfig.versionName

viewBinding = true

dependencies {
    implementation(Dependencies.appcompat)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.corektx)
    implementation(*Dependencies.koin)
    implementation(*Dependencies.lifecycle)
    implementation(Dependencies.material)
    implementation(*Dependencies.navigation)

    implementation(":core:design")
    implementation(":core:strings")
    implementation(":feature:map")
    implementation(":utils:android")
    implementation(":utils:kotlin")
}