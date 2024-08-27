import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    id("app.cash.zipline") version "1.16.0"
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("app.cash.zipline:zipline:1.16.0")
                implementation(project(":gameservice-shared"))
            }
        }
    }
}

zipline {
    mainFunction.set("com.parsumash.gameservice.launchZipline")
}

plugins.withType<YarnPlugin> {
    the<YarnRootExtension>().yarnLockAutoReplace = true
}
