plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.redwood.generator.widget)
}

kotlin {
    js { browser() }
    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(libs.redwood.layout.widget)
            api(libs.redwood.lazylayout.widget)
            api(projects.schema.modifiers)
            api(projects.values)
        }
    }
}

redwoodSchema {
    source = projects.schema
    type = "com.example.redwood.emojisearch.EmojiSearch"
}
