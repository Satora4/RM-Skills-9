plugins {
    `java-library`
}

allprojects {
    repositories {
        maven {
            url = uri("https://artifacts.ergon.ch/artifactory/proxy-maven-central/")
        }
    }

    pluginManager.withPlugin("java-library") {
        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(17))
            }
        }
    }
}

val dockerRepoPrefix by extra { "docker.ergon.ch/berufsbildung/wm-tippspiel" }

tasks {
    val pushDockerImages by creating() {
        group = "docker"
        dependsOn(":backend:jib")
        dependsOn(":frontend:pushDockerImage")
    }
}
