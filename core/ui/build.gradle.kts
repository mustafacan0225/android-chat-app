plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.mustafacan.android.base.plugin)
    alias(libs.plugins.mustafacan.android.ui.plugin)
    alias(libs.plugins.mustafacan.kotlin.serialization.plugin)
    id("org.jetbrains.kotlin.plugin.parcelize")
}

android {
    namespace = "com.mustafacan.core.ui"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.lottie.compose)
    implementation(libs.constraintlayout)
}