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
        mavenLocal()
    }
    val agpVersion = "7.1.3"
    val kotlinVersion = "1.6.21"
    plugins {
        kotlin("android") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        id("com.android.application") version agpVersion
        id("icu.nullptr.stringfuck") version "0.2.2"
    }
}

rootProject.name = "StringFuck"

include(":app")
include(":library")
include(":plugin")
include(":stub")
