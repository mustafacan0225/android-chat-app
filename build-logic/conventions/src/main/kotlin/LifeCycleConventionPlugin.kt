import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class LifeCycleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibs()
            dependencies {
                "implementation"(libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
                "implementation"(libs.findLibrary("lifecycle.runtime.compose").get())
                "implementation"(libs.findLibrary("lifecycle.viewmodel.compose").get())
            }
        }

    }
}