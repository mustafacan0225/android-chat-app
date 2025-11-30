plugins {
    alias(libs.plugins.mustafacan.android.feature.plugin)
}

android {
    namespace = "com.mustafacan.feature.rooms"
}

dependencies {
    implementation(libs.coil.compose)
}