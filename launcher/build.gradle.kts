plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.zipline)
}

kotlin {
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    androidTarget()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.presenterTreehouse)
            api(libs.redwood.treehouse.host)
            api(libs.zipline.loader)
        }
    }
}

android {
    namespace = "com.parsuomash.launcher"
}
