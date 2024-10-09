plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    js(IR) {
        moduleName = "compose-navigation"
        browser {
            commonWebpackConfig {
                sourceMaps = false
            }
        }
        binaries.executable()
    }

    sourceSets {
        jsMain.dependencies {
            implementation(npm("routes-js-module", "1.3.1"))
        }
    }
}
