import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    dependencies {
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.androidGradlePlugin)
        classpath(libs.buildConfigPlugin)
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-gradle-plugin`
}

dependencies {
    compileOnly(gradleApi())
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.gradleMavenPublishPlugin)
    implementation(libs.dokkaPlugin)
    implementation(libs.spotlessPlugin)
    implementation(libs.androidGradlePlugin)
    implementation(libs.kotlinx.binaryCompatibilityValidator)
    implementation(libs.zipline)

    // Expose the generated version catalog API to the plugin.
    implementation(files(libs::class.java.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        create("redwoodBuild") {
            id = "app.cash.redwood.build"
            displayName = "Redwood Build plugin"
            description = "Gradle plugin for Redwood build things"
            implementationClass = "app.cash.redwood.buildsupport.RedwoodBuildPlugin"
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}
