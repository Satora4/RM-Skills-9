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
  clean {
    doFirst {
      file("dist").deleteRecursively()
      file("node_modules").deleteRecursively()
    }
  }

  build {
    dependsOn("npmBuild")
  }

  val npmBuild by creating(NpmTask::class) {
    this.npmCommand.set(listOf("run", "build"))
  }

  val formatFrontend by creating(NpmTask::class) {
    this.npmCommand.set(listOf("run", "format"))
  }

  val npmStart by creating(NpmTask::class) {
    dependsOn(build)
    this.npmCommand.set(listOf("run", "start"))
  }

}
