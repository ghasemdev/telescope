plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    js { browser() }
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.okio)
            api(projects.schema.compose)
            api("app.cash.redwood:redwood-treehouse:0.14.0")
            api("app.cash.redwood:redwood-treehouse-guest-compose:0.14.0")
        }
    }
}
