import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class UiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibs()

            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            dependencies {
                "implementation"(platform(libs.findLibrary("androidx.compose.bom").get()))
                "androidTestImplementation"(platform(libs.findLibrary("androidx.compose.bom").get()))

                "implementation"(libs.findLibrary("material").get())
                "implementation"(libs.findLibrary("androidx.material3").get())
                "implementation"(libs.findLibrary("navigation.compose").get())

                "androidTestImplementation"(libs.findLibrary("androidx.ui.test.junit4").get())
                "debugImplementation"(libs.findLibrary("androidx.ui.tooling").get())
                "debugImplementation"(libs.findLibrary("androidx.ui.test.manifest").get())

            }
        }

    }
}