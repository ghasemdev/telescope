plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "navigation.js"
//                sourceMaps = false
            }
        }
        binaries.executable()
    }

    sourceSets {
        jsMain.dependencies {
            implementation(npm("routes-js-module", "1.1.0"))
        }
    }
}
