plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.zipline)
}
//
//redwoodBuild {
//    ziplineApplication("emoji-search")
//}

kotlin {
    js {
        // The name of the JS module which needs to be unique within the repo.
        moduleName = "emoji-search-presenter-treehouse"
        browser()
        binaries.executable()
    }
    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(libs.okio)
            api("app.cash.redwood:redwood-treehouse:0.14.0")
        }
        jsMain.dependencies {
            implementation(libs.okio)
            api(projects.schema.compose)
            api("app.cash.redwood:redwood-treehouse:0.14.0")
            api("app.cash.redwood:redwood-treehouse-guest-compose:0.14.0")

            api("app.cash.redwood:redwood-treehouse-guest:0.14.0")
            api(projects.presenter)
            implementation(projects.schema.protocolGuest)
        }
    }
}

zipline {
    mainFunction = "com.example.redwood.emojisearch.treehouse.preparePresenters"
}
