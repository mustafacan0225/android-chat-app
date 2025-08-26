plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidBaseConventionPlugin") {
            id = libs.plugins.android.base.convention.plugin.get().pluginId
            implementationClass = "AndroidBaseConventionPlugin"
        }

        register("androidAppConventionPlugin") {
            id = libs.plugins.android.app.convention.plugin.get().pluginId
            implementationClass = "AndroidAppConventionPlugin"
        }

        register("hiltUiConventionPlugin") {
            id = libs.plugins.hilt.ui.plugin.get().pluginId
            implementationClass = "HiltUiPlugin"
        }

        register("hiltCoreConventionPlugin") {
            id = libs.plugins.hilt.core.plugin.get().pluginId
            implementationClass = "HiltCorePlugin"
        }

        register("kotlinSerializationPlugin") {
            id = libs.plugins.kotlin.serialization.plugin.get().pluginId
            implementationClass = "KotlinSerializationPlugin"
        }

    }
}