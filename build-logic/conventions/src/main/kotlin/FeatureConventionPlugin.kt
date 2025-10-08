import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("mustafacan.android.base.convention.plugin")
            pluginManager.apply("mustafacan.hilt.ui.plugin")
            pluginManager.apply("mustafacan.android.ui.plugin")
            pluginManager.apply("mustafacan.android.lifecycle.plugin")

            pluginManager.withPlugin("com.android.library") {
                extensions.configure(LibraryExtension::class.java) {
                    buildFeatures {
                        compose = true
                    }
                }
            }

            dependencies {
                "implementation"(project(":core:ui"))
                "implementation"(project(":core:domain"))
                "implementation"(project(":core:model"))
            }
        }
    }
}