buildscript {
    repositories {
        google()
        mavenCentral()
        flatDir {
            dirs = setOf(rootProject.projectDir.resolve("plugin/build/libs"))
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath(":plugin:0.0.1")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
