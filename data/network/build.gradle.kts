plugins {
    alias(libs.plugins.mustafacan.android.data.plugin)
    alias(libs.plugins.mustafacan.paging.kotlin.plugin)
}

android {
    namespace = "com.mustafacan.data.network"

    buildFeatures {
        buildConfig = true
    }

    productFlavors {
        getByName("dev") {
            buildConfigField("String", "BASE_API_URL", "\"http://192.168.1.122:5000/api/\"")
            //buildConfigField("String", "BASE_API_URL", "\"http://192.168.43.55:5000/api/\"")
        }

        getByName("prod") {
            buildConfigField("String", "BASE_API_URL", "\"http://192.168.1.122:5000/api/\"")
            //buildConfigField("String", "BASE_API_URL", "\"http://192.168.43.55:5000/api/\"")
        }
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.logging.interceptor)
}