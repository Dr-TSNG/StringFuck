buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.18.0")
        classpath("icu.nullptr.stringfuck:gradle-plugin:0.1.1")
    }
}

val pluginGroup by extra("icu.nullptr.stringfuck")
val pluginVersion by extra("0.1")

val androidMinSdk by extra(24)
val androidTargetSdk by extra(32)

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.register("publishLocal") {
    dependsOn(":library:publishToMavenLocal")
    dependsOn(":plugin:publishToMavenLocal")
}
