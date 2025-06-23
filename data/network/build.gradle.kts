plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.mustafacan.data.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
    }

    flavorDimensions += "default"

    productFlavors {
        create("dev") {
            buildConfigField("String", "BASE_API_URL", "\"http://192.168.1.122:5000/api/\"")
            //buildConfigField("String", "BASE_API_URL", "\"http://192.168.43.55:5000/api/\"")
        }

        create("prod") {
            buildConfigField("String", "BASE_API_URL", "\"http://192.168.1.122:5000/api/\"")
            //buildConfigField("String", "BASE_API_URL", "\"http://192.168.43.55:5000/api/\"")
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)

    //logging-interceptor
    implementation(libs.logging.interceptor)

    //paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.common.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}