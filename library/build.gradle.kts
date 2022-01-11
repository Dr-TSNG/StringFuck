plugins {
    id("com.vanniktech.maven.publish")
    id("com.android.library")
    id("maven-publish")
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
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

mavenPublish {
    androidVariantToPublish = "release"
    sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01
}
