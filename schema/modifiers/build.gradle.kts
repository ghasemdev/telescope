plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.redwood.generator.modifiers)
}

kotlin {
    js { browser() }
    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        iosMain.dependencies {
            implementation(libs.androidx.annotation)
        }
    }
}

redwoodSchema {
    source = projects.schema
    type = "com.example.redwood.emojisearch.EmojiSearch"
}
