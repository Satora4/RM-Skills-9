plugins {
    `java-library`
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.github.node-gradle.node") version "3.3.0"
    id("com.google.cloud.tools.jib") version "3.2.1"
}

group = "ch.ergon.lernende.wmtippspiel"
version = "0.0.1-SNAPSHOT"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation(project("db-model"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core:9.1.6")
    implementation("org.flywaydb:flyway-mysql:9.16.0")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.4")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core:2.1.0")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

jib {
    from {
        image = "docker-mirror.ergon.ch/library/eclipse-temurin:17-jre"
    }
    to {
        image = "${rootProject.extra["dockerRepoPrefix"]}/${project.name}"
    }
}
