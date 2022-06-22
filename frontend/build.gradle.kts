import com.github.gradle.node.npm.task.NpxTask

plugins {
  `java-library`
  id("com.github.node-gradle.node") version "3.3.0"
}

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

  val buildAngularApp by creating(NpxTask::class) {
    dependsOn(npmInstall)
    command.set("ng")
    args.set(listOf("build"))
    inputs.files("package.json", "package-lock.json", "angular.json", "tsconfig.json", "tsconfig.app.json")
    inputs.dir("src")
    inputs.dir(fileTree("node_modules").exclude(".cache"))
    outputs.dir("dist")
  }

  val copyDistToResources by creating(Copy::class) {
    dependsOn(buildAngularApp)
    from("dist/wm-tippspiel-frontend")
    into(buildDir.resolve("angular-app/public"))
  }

  processResources {
    dependsOn(copyDistToResources)
  }
}

sourceSets {
  main {
    resources {
      srcDir(buildDir.resolve("angular-app"))
    }
  }
}
