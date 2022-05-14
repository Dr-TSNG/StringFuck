plugins {
    java
    `maven-publish`
    signing
}

dependencies {
    compileOnly(project(":stub"))
}

publishing {
    publications {
        create(project.name, MavenPublication::class) {
            from(components["java"])

            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}
