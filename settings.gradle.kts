dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    val agpVersion = "7.0.4"
    val kotlinVersion = "1.6.0"
    plugins {
        id("org.jetbrains.kotlin.android") version kotlinVersion
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("com.android.application") version agpVersion
        id("com.android.library") version agpVersion
    }
}

rootProject.name = "StringFuck"

include(":app")
include(":library")
include(":plugin")
include(":stub")
