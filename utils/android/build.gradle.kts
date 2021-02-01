plugins {
    id(Plugins.library)
    id(Plugins.common_android)
}

dependencies {
    implementation(*Dependencies.lifecycle)
    implementation(Dependencies.location)

    implementation(":utils:kotlin")
}