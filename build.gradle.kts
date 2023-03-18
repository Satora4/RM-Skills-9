import org.gradle.internal.impldep.org.eclipse.jgit.lib.ObjectChecker.type

plugins {
    `java-library`
}

allprojects {
    repositories {
        mavenCentral()
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

// tasks.register("pushDockerImages") {
//     group = "docker"
//     dependsOn(project.tasks.named(":backend:jib"))
//     dependsOn(project.tasks.named(":frontend:pushDockerImage"))
// }
