import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class PagingComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibs()
            dependencies {
                "implementation"(libs.findLibrary("paging.compose").get())
            }
        }
    }
}