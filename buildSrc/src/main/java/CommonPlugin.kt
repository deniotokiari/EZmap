import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies

open class CommonPlugin : Plugin<Project> {

    protected open val plugins: (PluginContainer.() -> Unit)? = null

    protected open val java: (JavaPluginExtension.() -> Unit)? = null

    protected open val android: (BaseExtension.() -> Unit)? = null

    protected open val dependencies: (DependencyHandlerScope.() -> Unit)? = null

    final override fun apply(target: Project) {
        plugins?.invoke(target.plugins)

        java?.let { java(target, it) }

        android?.let { android(target, it) }

        dependencies?.let { target.dependencies(it) }
    }
}