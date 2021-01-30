import com.android.build.gradle.BaseExtension
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope

@Suppress("unused")
class CommonAndroidPlugin : CommonPlugin() {

    override val plugins: (PluginContainer.() -> Unit)? = {
        apply("kotlin-android")
    }

    override val android: (BaseExtension.() -> Unit)? = {
        compileSdkVersion(AppConfig.compileSdkVersion)
        buildToolsVersion(AppConfig.buildToolsVersion)

        defaultConfig {
            minSdkVersion(AppConfig.minSdkVersion)
            targetSdkVersion(AppConfig.targetSdkVersion)

            versionCode = 1
            versionName = "1.0"
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = false

                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        @kotlin.Suppress("UnstableApiUsage")
        compileOptions {
            sourceCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
            targetCompatibility = org.gradle.api.JavaVersion.VERSION_1_8
        }
    }

    override val dependencies: (DependencyHandlerScope.() -> Unit)? = {
        implementation(Dependencies.kotlin_stdlib)
    }
}