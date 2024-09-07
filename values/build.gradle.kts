plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    js { browser() }
    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.kotlinx.serialization.core)
            api("app.cash.redwood:redwood-treehouse:0.14.0")
            implementation("app.cash.redwood:redwood-widget:0.14.0")
        }
    }
}
