plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    kotlin("jvm")
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.4")
    implementation("com.android.tools.build:gradle-api:7.0.4")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

gradlePlugin {
    @Suppress("UNUSED_VARIABLE")
    val stringFuck by plugins.creating {
        id = "icu.nullptr.stringfuck"
        implementationClass = "icu.nullptr.stringfuck.StringFuckPlugin"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = rootProject.extra["pluginGroup"] as String
            artifactId = "gradle-plugin"
            version = rootProject.extra["pluginVersion"] as String

            from(components["java"])
        }
    }
}
