import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class PagingKotlinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibs()
            dependencies {
                "implementation"(libs.findLibrary("paging.runtime").get())
                "implementation"(libs.findLibrary("paging.common.ktx").get())
            }
        }
    }
}