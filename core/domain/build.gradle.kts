plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.mustafacan.android.base.plugin)
    alias(libs.plugins.mustafacan.kotlin.serialization.plugin)
    alias(libs.plugins.mustafacan.paging.kotlin.plugin)
}

android {
    namespace = "com.mustafacan.core.domain"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.javax.inject)
    implementation(libs.gson)
}