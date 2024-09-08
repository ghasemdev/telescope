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
            api(libs.redwood.treehouse)
            api(libs.redwood.treehouse.guest.compose)
        }
    }
}
