import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.project

private fun DependencyHandler.addWithProjectModuleCheck(type: String, vararg items: String) {
    items.forEach { item ->
        if (item.startsWith(":")) {
            add(type, project(item))
        } else {
            add(type, item)
        }
    }
}

fun DependencyHandler.implementation(vararg items: String) {
    addWithProjectModuleCheck("implementation", *items)
}

fun DependencyHandler.api(vararg items: String) {
    addWithProjectModuleCheck("api", *items)
}

fun android(project: Project, block: BaseExtension.() -> Unit) {
    (project as ExtensionAware).extensions.configure("android", block)
}

fun java(project: Project, block: JavaPluginExtension.() -> Unit) {
    (project as ExtensionAware).extensions.configure("java", block)
}

private val Project.defaultConfig: DefaultConfig?
    get() = (extensions.findByName("android") as? BaseExtension)?.defaultConfig

var Project.versionCode: Int?
    set(value) {
        defaultConfig?.versionCode = value
    }
    get() = defaultConfig?.versionCode

var Project.versionName: String?
    set(value) {
        defaultConfig?.versionName = value
    }
    get() = defaultConfig?.versionName