import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`

    kotlin("jvm") version "1.6.10"
}

group = "space.maxus"
version = "1.0.0-stable"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")

    api("org.jetbrains.kotlin:kotlin-reflect:1.6.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    repositories {
        maven("https://repo.repsy.io/mvn/maxuss/artifacts") {
            this.credentials.username = properties["REPO_USERNAME"] as String?
            this.credentials.password = properties["REPO_PASSWORD"] as String?
        }

        publications {
            create<MavenPublication>(project.name) {
                artifact(tasks.named("javadocJar"))
                artifact(tasks.named("sourcesJar"))

                this.groupId = project.group.toString()
                this.artifactId = project.name.toLowerCase()
                this.version = project.version.toString()

                pom {
                    name.set(project.name)
                    description.set(project.description)

                    developers {
                        developer {
                            name.set("Maxuss")
                        }
                    }

                    licenses {
                        license {
                            name.set("The MIT License (MIT)")
                            url.set("https://mit-license.org")
                        }
                    }

                    url.set("https://github.com/Maxuss")

                    scm {
                        connection.set("scm:git:git://github.com/Maxuss/CDLib.git")
                        url.set("https://github.com/Maxuss/CDLib/tree/master")
                    }
                }
            }
        }
    }
}