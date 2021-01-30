plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("common-android-plugin") {
            id = "common-android-plugin"
            implementationClass = "CommonAndroidPlugin"
        }

        register("common-kotlin-plugin") {
            id = "common-kotlin-plugin"
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