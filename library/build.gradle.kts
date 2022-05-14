plugins {
    id("com.vanniktech.maven.publish")
    id("com.android.library")
}

val androidMinSdk: Int by rootProject.extra
val androidTargetSdk: Int by rootProject.extra

android {
    compileSdk = androidTargetSdk

    defaultConfig {
        minSdk = androidMinSdk
        targetSdk = androidTargetSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    compileOnly(project(":stub"))
}
