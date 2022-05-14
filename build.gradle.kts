import com.vanniktech.maven.publish.MavenPublishPluginExtension
import org.gradle.plugins.signing.SigningExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.19.0")
    }
}

val pluginGroup by extra("icu.nullptr.stringfuck")

val androidMinSdk by extra(24)
val androidTargetSdk by extra(32)

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.register("publishLocal") {
    dependsOn(":library:publishToMavenLocal")
    dependsOn(":plugin:publishToMavenLocal")
}

subprojects {
    group = "icu.nullptr.stringfuck"
    version = "0.2.1"

    plugins.withId("com.vanniktech.maven.publish") {
        extensions.findByType(SigningExtension::class.java)!!.run {
            useGpgCmd()
        }
        extensions.findByType(MavenPublishPluginExtension::class.java)!!.run {
            sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01
        }
    }
}
