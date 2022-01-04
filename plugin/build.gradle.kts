plugins {
    id("java-gradle-plugin")
    kotlin("jvm")
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.4")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

gradlePlugin {
    val stringFuck by plugins.creating {
        id = "icu.nullptr.stringfuck"
        implementationClass = "icu.nullptr.stringfuck.StringFuckPlugin"
    }
}
