import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class BaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            val libs = getLibs()

            pluginManager.apply("org.jetbrains.kotlin.android")

            pluginManager.withPlugin("com.android.library") {
                extensions.configure(LibraryExtension::class.java) {

                    buildTypes {
                        getByName("debug") {
                            isMinifyEnabled = false
                            consumerProguardFiles("consumer-rules.pro")
                        }

                        getByName("release") {
                            isMinifyEnabled = true
                            consumerProguardFiles("consumer-rules.pro")
                        }
                    }

                    flavorDimensions += "default"

                    productFlavors {
                        create("dev") {
                        }

                        create("prod") {
                        }
                    }
                }
            }

            extensions.configure<BaseExtension> {
                compileSdkVersion(35)

                defaultConfig {
                    minSdk = 24
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                tasks.withType<KotlinCompile>().configureEach {
                    compilerOptions {
                        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
                    }
                }
            }

            dependencies {
                "implementation"(libs.findLibrary("androidx.core.ktx").get())
                "implementation"(libs.findLibrary("androidx.appcompat").get())
                "testImplementation"(libs.findLibrary("junit").get())
                "androidTestImplementation"(libs.findLibrary("androidx.junit").get())
                "androidTestImplementation"(libs.findLibrary("androidx.espresso.core").get())

            }
        }
    }
}