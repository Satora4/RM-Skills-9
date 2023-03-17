import nu.studer.gradle.jooq.JooqEdition
import nu.studer.gradle.jooq.JooqGenerate
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property
import org.jooq.util.jaxb.tools.XMLAppendable

plugins {
    `java-library`
    id("nu.studer.jooq") version "7.1.1"
    id("org.flywaydb.flyway") version "9.1.3"
}

buildscript {
    dependencies {
        classpath(group = "org.mariadb.jdbc", name = "mariadb-java-client", version = "2.7.4")
        classpath("org.flywaydb:flyway-mysql:9.16.0")
    }
}

dependencies {
    api(group = "org.jooq", name = "jooq", version = "3.15.4")

    jooqGenerator(project(":backend:jooq-codegen-customization"))
    jooqGenerator(group = "org.mariadb.jdbc", name = "mariadb-java-client", version = "2.7.4")
}

val dbUsername by extra("root")
val dbPassword by extra("password")
val codegenDirectory = buildDir.resolve("jooqCodeGen")
val dbDirectory = codegenDirectory.resolve("db")
val jdbcUrl = "jdbc:mariadb://localhost:3306/rm-skills-9"

flyway {
    // allows insertions via migration scripts
    mixed = true
    url = jdbcUrl
    user = dbUsername
    password = dbPassword
    cleanDisabled = false
}

tasks {
    val deleteDbDirectory by registering(Delete::class) {
        delete(dbDirectory)
    }

    withType<JooqGenerate> {
        dependsOn(named("flywayMigrate"))
        finalizedBy(deleteDbDirectory)
    }
}

jooq {
    version.set("3.16.7")
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration {
                logging = Logging.WARN
                jdbc {
                    driver = "org.mariadb.jdbc.Driver"
                    url = jdbcUrl
                    user = dbUsername
                    password = dbPassword
                    properties.add(Property().withKey("PAGE_SIZE").withValue("2048"))
                }
                generator {
                    name = "org.jooq.codegen.JavaGenerator"
                    database {
                        name = "org.jooq.meta.mariadb.mariadbDatabase"
                        excludes = "(?i)flyway_schema_history"
                        inputSchema = "public"
                    }
                    generate {
                        isDeprecated = false
                        isFluentSetters = false
                        isGeneratedAnnotation = false
                        isImmutablePojos = false
                        isJavaTimeTypes = true
                        isPojos = false
                        isRecords = true
                    }
                    target {
                        packageName = "ch.ergon.lernenden.backend.db"
                        directory = "$codegenDirectory/src/"
                    }
                    strategy {
                        name = "ch.ergon.lernende.backend.GeneratorStrategy"
                    }
                }
            }
        }
    }
}

inline operator fun <T : XMLAppendable> T.invoke(block: T.() -> Unit) {
    block()
}
