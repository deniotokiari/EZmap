import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.project

private fun DependencyHandler.addWithProjectModuleCheck(type: String, item: String) {
    if (item.startsWith(":")) {
        add(type, project(item))
    } else {
        add(type, item)
    }
}

fun DependencyHandler.implementation(item: String) {
    addWithProjectModuleCheck("implementation", item)
}

fun DependencyHandler.api(item: String) {
    addWithProjectModuleCheck("api", item)
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