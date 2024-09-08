rootProject.name = "Telescope"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":composeApp")
include(":gameservice")
include(":gameservice-shared")
include(":presenter")
include(":presenter-treehouse")
include(":schema")
include(":schema:compose")
include(":schema:widget")
include(":schema:modifiers")
include(":schema:protocol-guest")
include(":values")
