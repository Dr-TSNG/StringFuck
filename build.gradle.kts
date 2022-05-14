import com.android.build.gradle.BaseExtension
import org.gradle.plugins.signing.SigningExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
    }
}

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
    version = "0.2.2"

    plugins.withId("java") {
        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        tasks.register("sourcesJar", type = Jar::class) {
            archiveClassifier.set("sources")
            from(project.extensions.getByType<SourceSetContainer>().getByName("main").allSource)
        }
        tasks.register("javadocJar", type = Jar::class) {
            archiveClassifier.set("javadoc")
            from(tasks["javadoc"])
        }
        tasks.withType(Javadoc::class) {
            isFailOnError = false
        }
    }

    plugins.withId("maven-publish") {
        extensions.configure<PublishingExtension> {
            publications {
                withType(MavenPublication::class) {
                    version = project.version as String
                    group = project.group as String

                    pom {
                        name.set("StringFuck")
                        description.set("Yet Another String Obfuscator for Android")
                        url.set("https://github.com/Dr-TSNG/StringFuck")
                        licenses {
                            license {
                                name.set("Apache License 2.0")
                                url.set("https://github.com/Dr-TSNG/StringFuck/blob/master/LICENSE")
                            }
                        }
                        developers {
                            developer {
                                name.set("Nullptr")
                                url.set("https://github.com/Dr-TSNG")
                            }
                        }
                        scm {
                            connection.set("scm:git:https://github.com/Dr-TSNG/StringFuck.git")
                            url.set("https://github.com/Dr-TSNG/StringFuck")
                        }
                    }
                }
                repositories {
                    mavenLocal()
                    maven {
                        name = "ossrh"
                        url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
                        credentials(PasswordCredentials::class.java)
                    }
                }
            }
        }
    }

    plugins.withId("signing") {
        extensions.configure<SigningExtension> {
            if (findProperty("signing.gnupg.keyName") != null) {
                useGpgCmd()

                plugins.withId("maven-publish") {
                    extensions.configure<PublishingExtension> {
                        publications {
                            withType(MavenPublication::class) {
                                val signingTasks = sign(this)
                                tasks.withType(AbstractPublishToMaven::class).matching { it.publication == this }.all {
                                    dependsOn(signingTasks)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
