object Dependencies {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val gradle_api = "com.android.tools.build:gradle-api:${Versions.gradle}"

    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val corektx = "androidx.core:core-ktx:${Versions.corektx}"
    val koin = arrayOf(
        "org.koin:koin-androidx-scope:${Versions.koin}",
        "org.koin:koin-androidx-viewmodel:${Versions.koin}",
        "org.koin:koin-androidx-fragment:${Versions.koin}",
        "org.koin:koin-androidx-ext:${Versions.koin}"
    )
    val lifecycle = arrayOf(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}",
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    )
    const val material = "com.google.android.material:material:${Versions.material}"
    val navigation = arrayOf(
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}",
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    )
    const val navigation_safe_args = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
}

object Plugins {
    const val application = "com.android.application"
    const val common_android = "common-android-plugin"
    const val common_kotlin = "common-kotlin-plugin"
    const val library = "com.android.library"
    const val navigation = "androidx.navigation.safeargs.kotlin"
    const val versions = "com.github.ben-manes.versions"
}