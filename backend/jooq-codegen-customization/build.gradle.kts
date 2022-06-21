
plugins {
  `java-library`
    kotlin("jvm") version "1.5.31"
}

dependencies {
    implementation(group = "org.jooq", name = "jooq-codegen", version = "3.15.4")
}