import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class DataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("mustafacan.android.base.convention.plugin")
            pluginManager.apply("mustafacan.hilt.core.plugin")

            dependencies {
                "implementation"(project(":core:domain"))
                "implementation"(project(":core:model"))
            }
        }
    }
}