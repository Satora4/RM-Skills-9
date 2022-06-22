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
