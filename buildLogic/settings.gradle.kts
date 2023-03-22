dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
    versionCatalog {
        create("libs") {
            from(files("../gradle/libs.version.toml"))
        }
    }
}