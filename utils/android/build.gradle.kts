plugins {
    id(Plugins.library)
    id(Plugins.common_android)
}

dependencies {
    implementation(*Dependencies.lifecycle)

    implementation(":utils:kotlin")
}