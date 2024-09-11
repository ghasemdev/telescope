plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.zipline)
}

redwoodBuild {
    ziplineApplication("emoji-search")
}

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
            api(libs.redwood.treehouse)
        }
        jsMain.dependencies {
            implementation(libs.okio)
            api(projects.schema.compose)
            api(libs.redwood.treehouse)
            api(libs.redwood.treehouse.guest.compose)

            api(libs.redwood.treehouse.guest)
            api(projects.presenter)
            implementation(projects.schema.protocolGuest)
        }
    }
}

zipline {
    mainFunction = "com.parsumash.treehouse.preparePresenters"
}
