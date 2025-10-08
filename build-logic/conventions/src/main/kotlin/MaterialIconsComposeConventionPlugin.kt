import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MaterialIconsComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibs()
            dependencies {
                "implementation"(libs.findLibrary("compose.material.icons.extended").get())
            }
        }
    }
}