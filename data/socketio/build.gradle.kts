plugins {
    alias(libs.plugins.mustafacan.android.data.plugin)
    alias(libs.plugins.mustafacan.moshi.plugin)
}

android {
    namespace = "com.mustafacan.data.socketio"

    buildFeatures {
        buildConfig = true
    }

    productFlavors {
        getByName("dev") {
            buildConfigField("String", "SOCKET_URL", "\"http://192.168.1.122:5000\"")
            //buildConfigField("String", "SOCKET_URL", "\"http://192.168.43.55:5000\"")
        }

        getByName("prod") {
            //buildConfigField("String", "SOCKET_URL", "\"http://10.0.2.2:5000\"")
            buildConfigField("String", "SOCKET_URL", "\"http://192.168.1.122:5000\"")
            //buildConfigField("String", "SOCKET_URL", "\"http://192.168.43.55:5000\"")
        }
    }
}

dependencies {
    implementation(libs.socketio) {
        exclude(group = "org.json", module = "json")
    }
}