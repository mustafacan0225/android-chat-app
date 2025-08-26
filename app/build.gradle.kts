plugins {
    alias(libs.plugins.android.app.convention.plugin)
    alias(libs.plugins.hilt.ui.plugin)

    alias(libs.plugins.kotlin.compose)
    //alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.kotlin.serialization.plugin)
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:appevent"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:users"))
    implementation(project(":feature:chat"))

    //data:network module is not used in app module, it is added only for hilt dependencies
    implementation(project(":data:network"))

    //data:datastore module is not used in app module, it is added only for hilt dependencies
    implementation(project(":data:datastore"))

    //data:datastore module is not used in app module, it is added only for hilt dependencies
    implementation(project(":data:socketio"))

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))


    implementation(libs.androidx.material3)
    implementation(libs.lifecycle.runtime.compose)

    implementation(libs.navigation.compose)
    implementation(libs.lifecycle.viewmodel.compose)

    //implementation(libs.kotlinx.serialization.json)





}