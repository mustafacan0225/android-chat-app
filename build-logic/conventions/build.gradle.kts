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
            id = libs.plugins.mustafacan.android.base.plugin.get().pluginId
            implementationClass = "BaseConventionPlugin"
        }

        register("androidAppConventionPlugin") {
            id = libs.plugins.mustafacan.android.app.plugin.get().pluginId
            implementationClass = "AppConventionPlugin"
        }

        register("hiltUiConventionPlugin") {
            id = libs.plugins.mustafacan.hilt.ui.plugin.get().pluginId
            implementationClass = "HiltUiConventionPlugin"
        }

        register("hiltCoreConventionPlugin") {
            id = libs.plugins.mustafacan.hilt.core.plugin.get().pluginId
            implementationClass = "HiltCoreConventionPlugin"
        }

        register("kotlinSerializationPlugin") {
            id = libs.plugins.mustafacan.kotlin.serialization.plugin.get().pluginId
            implementationClass = "KotlinSerializationConventionPlugin"
        }

        register("androidUiPlugin") {
            id = libs.plugins.mustafacan.android.ui.plugin.get().pluginId
            implementationClass = "UiConventionPlugin"
        }

        register("androidLifeCyclePlugin") {
            id = libs.plugins.mustafacan.android.lifecycle.plugin.get().pluginId
            implementationClass = "LifeCycleConventionPlugin"
        }

        register("androidFeatureConventionPlugin") {
            id = libs.plugins.mustafacan.android.feature.plugin.get().pluginId
            implementationClass = "FeatureConventionPlugin"
        }

        register("androidDataConventionPlugin") {
            id = libs.plugins.mustafacan.android.data.plugin.get().pluginId
            implementationClass = "DataConventionPlugin"
        }

        register("androidPagingComposeConventionPlugin") {
            id = libs.plugins.mustafacan.paging.compose.plugin.get().pluginId
            implementationClass = "PagingComposeConventionPlugin"
        }

        register("androidMaterialIconsComposeConventionPlugin") {
            id = libs.plugins.mustafacan.material.icon.compose.plugin.get().pluginId
            implementationClass = "MaterialIconsComposeConventionPlugin"
        }

        register("kotlinMoshiConventionPlugin") {
            id = libs.plugins.mustafacan.moshi.plugin.get().pluginId
            implementationClass = "MoshiConventionPlugin"
        }

        register("androidPagingKotlinConventionPlugin") {
            id = libs.plugins.mustafacan.paging.kotlin.plugin.get().pluginId
            implementationClass = "PagingKotlinConventionPlugin"
        }



    }
}