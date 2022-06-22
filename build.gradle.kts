
allprojects {
    repositories {
        maven { setUrl("https://nexus.ergon.ch/repository/secure-public") }
        maven { setUrl("https://nexus.ergon.ch/repository/intermediates") }
    }
}
