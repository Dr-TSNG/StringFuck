plugins {
    id("com.android.application")
    id("icu.nullptr.stringfuck")
    kotlin("android")
}

val androidMinSdk: Int by rootProject.extra
val androidTargetSdk: Int by rootProject.extra

android {
    compileSdk = androidTargetSdk

    defaultConfig {
        applicationId = "icu.nullptr.stringfuck.unittest"
        minSdk = androidMinSdk
        targetSdk = androidTargetSdk
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

stringFuck {
    isPrintDebugInfo = false
    isWorkOnDebug = true
    isWhiteList = true

    setKey("nullptr")
}

dependencies {
    implementation(project(":library"))
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
