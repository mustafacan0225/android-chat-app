plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.mustafacan.data.socketio"
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
            //buildConfigField("String", "SOCKET_URL", "\"http://192.168.1.122:5000\"")
            buildConfigField("String", "SOCKET_URL", "\"http://192.168.43.55:5000\"")


        }

        create("prod") {
            //buildConfigField("String", "SOCKET_URL", "\"http://10.0.2.2:5000\"")
            //buildConfigField("String", "SOCKET_URL", "\"http://192.168.1.122:5000\"")
            buildConfigField("String", "SOCKET_URL", "\"http://192.168.43.55:5000\"")
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    // socketio
    implementation(libs.socketio) {
        exclude(group = "org.json", module = "json")
    }
}