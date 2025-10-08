plugins {
    alias(libs.plugins.mustafacan.android.data.plugin)
    alias(libs.plugins.mustafacan.moshi.plugin)
}

android {
    namespace = "com.mustafacan.data.datastore"
}

dependencies {
    implementation(libs.datastore.preferences)
}
