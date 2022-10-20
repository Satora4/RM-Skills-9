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
        classpath(group = "org.postgresql", name = "postgresql", version = "42.4.2")
    }
}

dependencies {
    api(group = "org.jooq", name = "jooq", version = "3.15.4")

    jooqGenerator(project(":backend:jooq-codegen-customization"))
    jooqGenerator(group = "org.postgresql", name = "postgresql", version = "42.4.2")
}

val dbUsername by extra("wm-tippspiel")
val dbPassword by extra("TcZvs3AfLKhKJtbkTp")
val codegenDirectory = buildDir.resolve("jooqCodeGen")
val dbDirectory = codegenDirectory.resolve("db")
val jdbcUrl = "jdbc:postgresql://localhost:5432/wm-tippspiel"

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
                    driver = "org.postgresql.Driver"
                    url = jdbcUrl
                    user = dbUsername
                    password = dbPassword
                    properties.add(Property().withKey("PAGE_SIZE").withValue("2048"))
                }
                generator {
                    name = "org.jooq.codegen.JavaGenerator"
                    database {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
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
