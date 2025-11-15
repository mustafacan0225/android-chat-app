import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = getLibs()
            pluginManager.apply("com.android.application")
            pluginManager.apply("mustafacan.android.base.convention.plugin")
            pluginManager.apply("mustafacan.hilt.ui.plugin")
            pluginManager.apply("mustafacan.kotlin.serialization.plugin")
            pluginManager.apply("mustafacan.android.ui.plugin")
            pluginManager.apply("mustafacan.android.lifecycle.plugin")

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

                buildTypes {
                    getByName("debug") {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }

                    getByName("release") {
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
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
                "implementation"(project(":core:ui"))
                "implementation"(project(":core:appevent"))
                "implementation"(project(":core:domain"))
                "implementation"(project(":core:model"))
                "implementation"(project(":feature:auth"))
                "implementation"(project(":feature:users"))
                "implementation"(project(":feature:chat"))
                "implementation"(project(":feature:messages"))

                //data:network module is not used in app module, it is added only for hilt dependencies
                "implementation"(project(":data:network"))

                //data:datastore module is not used in app module, it is added only for hilt dependencies
                "implementation"(project(":data:datastore"))

                //data:datastore module is not used in app module, it is added only for hilt dependencies
                "implementation"(project(":data:socketio"))

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