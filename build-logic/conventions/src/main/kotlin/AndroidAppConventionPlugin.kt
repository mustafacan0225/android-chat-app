import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.kotlin.dsl.dependencies

class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibs()
            pluginManager.apply("com.android.application")

            //AndroidBaseConventionPlugin
            pluginManager.apply("mustafacan.android.base.convention.plugin")

            extensions.configure<ApplicationExtension> {
                namespace = "com.mustafacan.android_chat_app"
                defaultConfig {
                    applicationId = "com.mustafacan.android_chat_app"
                    targetSdk = (35)
                    versionCode = 1
                    versionName = "1.0"
                }

                buildFeatures {
                    compose = true
                }

                flavorDimensions += "default"

                productFlavors {
                    create("dev") {
                        dimension = "default"
                        applicationId = "com.mustafacan.android_chat_app.dev"
                        resValue("string", "app_name", "Chat App (Test)")
                    }

                    create("prod") {
                        dimension = "default"
                        applicationId = "com.mustafacan.android_chat_app"
                        resValue("string", "app_name", "Chat App (Prod)")
                    }
                }
            }

            dependencies {
                "implementation"(libs.findLibrary("androidx.activity.compose").get())
                "implementation"(libs.findLibrary("androidx.ui").get())
                "implementation"(libs.findLibrary("androidx.ui.graphics").get())
                "implementation"(libs.findLibrary("androidx.ui.tooling.preview").get())

                "androidTestImplementation"(libs.findLibrary("androidx.compose.bom").get())
                "androidTestImplementation"(libs.findLibrary("androidx.ui.test.junit4").get())
                "debugImplementation"(libs.findLibrary("androidx.ui.tooling").get())
                "debugImplementation"(libs.findLibrary("androidx.ui.test.manifest").get())

            }

        }
    }
}