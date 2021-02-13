plugins {
    id(Plugins.application)
    id(Plugins.common_android)
    id(Plugins.navigation)
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
    implementation(":core:navigation")
    implementation(":core:strings")
    implementation(":core:tiles-provider")
    implementation(":feature:map")
    implementation(":feature:permissions-handler")
    implementation(":utils:android")
    implementation(":utils:kotlin")
}