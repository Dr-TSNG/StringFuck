plugins {
    id("com.android.application")
    id("icu.nullptr.stringfuck")
    kotlin("android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "icu.nullptr.stringfuck.unittest"
        minSdk = 24
        targetSdk = 32
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
    isPrintDebugInfo = true
    isWorkOnDebug = true
    isWhiteList = true

    obfuscationList = setOf("androidx")

    encryptMethod = { str -> str.encodeToByteArray() }
    decryptMethodClassPath = "icu.nullptr.stringfuck.unittest.MyDecryptor"

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
