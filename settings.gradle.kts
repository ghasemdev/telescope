rootProject.name = "Telescope"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-support/redwood-settings")

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

plugins {
    id("app.cash.redwood.settings")
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

includeBuild("build-support") {
    dependencySubstitution {
        substitute(module("app.cash.redwood.build:gradle-plugin")).using(project(":"))
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
