plugins {
    `java-library`
}

allprojects {
    repositories {
        maven { setUrl("https://nexus.ergon.ch/repository/secure-public") }
        maven { setUrl("https://nexus.ergon.ch/repository/intermediates") }
    }

    pluginManager.withPlugin("java-library") {
        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(17))
            }
        }
    }
}

val dockerRepoPrefix by extra {"docker.ergon.ch/berufsbildung/wm-tippspiel"}

tasks {
    val pushDockerImages by creating() {
        group = "docker"
        dependsOn(":backend:jib")
        dependsOn(":frontend:pushDockerImage")
    }
}
