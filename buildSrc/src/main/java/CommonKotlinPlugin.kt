import org.gradle.api.JavaVersion
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope

@Suppress("unused")
class CommonKotlinPlugin : CommonPlugin() {

    override val plugins: (PluginContainer.() -> Unit)? = {
        apply("java-library")
        apply("kotlin")
    }

    override val java: (JavaPluginExtension.() -> Unit)? = {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    override val dependencies: (DependencyHandlerScope.() -> Unit)? = {
        implementation(Dependencies.kotlin_stdlib)
    }
}