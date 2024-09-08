rootProject.name = "build-support"

include(":redwood-gradle-plugin")
project(":redwood-gradle-plugin").projectDir = File("../redwood-gradle-plugin")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
