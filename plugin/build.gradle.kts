plugins {
    java
    `java-gradle-plugin`
    `maven-publish`
    signing
    kotlin("jvm")
}

dependencies {
    implementation("com.android.tools.build:gradle:7.1.3")
    implementation("com.android.tools.build:gradle-api:7.1.3")
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

gradlePlugin {
    plugins.create("stringFuck") {
        id = project.group as String
        displayName = "StringFuck"
        description = "Yet Another String Obfuscator for Android"
        implementationClass = "$group.StringFuckPlugin"
    }
}

afterEvaluate {
    publishing {
        publications {
            named("pluginMaven", MavenPublication::class) {
                artifactId = project.name

                artifact(tasks["sourcesJar"])
                artifact(tasks["javadocJar"])
            }
        }
    }
}
