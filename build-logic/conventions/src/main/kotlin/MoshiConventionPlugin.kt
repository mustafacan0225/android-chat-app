import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MoshiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibs()
            dependencies {
                "implementation"(libs.findLibrary("moshi").get())
                "implementation"(libs.findLibrary("moshi.kotlin").get())
            }
        }
    }
}