import nu.studer.gradle.jooq.JooqEdition
import nu.studer.gradle.jooq.JooqGenerate
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property
import org.jooq.util.jaxb.tools.XMLAppendable

plugins {
    `java-library`
    id("nu.studer.jooq") version "6.0.1"
    id("org.flywaydb.flyway") version "7.9.1"
}

buildscript {
    dependencies {
        classpath(group = "com.h2database", name = "h2", version = "1.4.200")
    }
}

dependencies {
    api(group = "org.jooq", name = "jooq", version = "3.15.4")

    jooqGenerator(project(":backend:jooq-codegen-customization"))
    jooqGenerator(group = "com.h2database", name = "h2", version = "1.4.200")
}

val codegenDirectory = buildDir.resolve("jooqCodeGen")

val dbDirectory = codegenDirectory.resolve("db")

val jdbcUrl = "jdbc:h2:file:${dbDirectory.resolve("migration")}"

flyway {
    val scriptPath = projectDir.resolve("scripts").resolve("init.sql").toString().replace("\\", "\\\\")
    // allows insertions via migration scripts
    mixed = true
    url = "$jdbcUrl;INIT=RUNSCRIPT FROM '$scriptPath'"
    user = "sa"
}

tasks {
    val deleteDbDirectory by registering(Delete::class) {
        delete(dbDirectory)
    }

    withType<JooqGenerate> {
        dependsOn(flywayMigrate)
        finalizedBy(deleteDbDirectory)
    }
}

jooq {
    version.set("3.15.4")
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration {
                logging = Logging.WARN
                jdbc {
                    driver = "org.h2.Driver"
                    url = jdbcUrl
                    user = "sa"
                    password = ""
                    properties.add(Property().withKey("PAGE_SIZE").withValue("2048"))
                }
                generator {
                    name = "org.jooq.codegen.JavaGenerator"
                    database {
                        name = "org.jooq.meta.h2.H2Database"
                        inputSchema = "WM_TIPPSPIEL"
                        excludes = "(?i)flyway_schema_history"
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
                        packageName = "ch.ergon.lernenden.wmtippspiel.backend.db"
                        directory = "$codegenDirectory/src/"
                    }
                    strategy {
                        name = "ch.ergon.lernende.wmtippspiel.backend.WmTippspielGeneratorStrategy"
                    }
                }
            }
        }
    }
}

inline operator fun <T : XMLAppendable> T.invoke(block: T.() -> Unit) {
    block()
}