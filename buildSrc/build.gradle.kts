plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register(Plugins.common_android) {
            id = Plugins.common_android
            implementationClass = "CommonAndroidPlugin"
        }

        register(Plugins.common_kotlin) {
            id = Plugins.common_kotlin
            implementationClass = "CommonKotlinPlugin"
        }
    }
}

repositories {
    google()
    jcenter()
}

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/java")
}

dependencies {
    implementation(Dependencies.gradle)
    implementation(Dependencies.gradle_api)
    implementation(Dependencies.kotlin_gradle_plugin)
    implementation(Dependencies.kotlin_stdlib_jdk8)
}