import com.github.gradle.node.npm.task.NpmTask

plugins {
  `java-library`
  id("com.github.node-gradle.node") version "3.3.0"
}

apply(from = "devops/docker.gradle")

node {
  download.set(true)
}

tasks {
  named("clean") {
    doFirst {
      file("dist").deleteRecursively()
      file("node_modules").deleteRecursively()
    }
  }

  register<NpmTask>("npmBuild") {
    dependsOn(named("npmInstall"))
    this.npmCommand.set(listOf("run", "build"))
  }

  register<NpmTask>("formatFrontend") {
    this.npmCommand.set(listOf("run", "format"))
  }

  register<NpmTask>("npmStart") {
    dependsOn(named("build"))
    this.npmCommand.set(listOf("run", "start"))
  }

  named("build") {
    dependsOn(named("npmBuild"))
  }
}
