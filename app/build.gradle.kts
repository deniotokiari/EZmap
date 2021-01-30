plugins {
    id(Plugins.application)
    id(Plugins.common_android)
}

versionCode = AppConfig.versionCode
versionName = AppConfig.versionName

dependencies {
    implementation(Dependencies.appcompat)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.corektx)
    implementation(Dependencies.material)

    implementation(":core:design")
    implementation(":core:strings")
    implementation(":utils:android")
    implementation(":utils:kotlin")
}