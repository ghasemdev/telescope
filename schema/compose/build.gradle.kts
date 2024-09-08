plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.redwood.generator.compose)
}

kotlin {
    js { browser() }
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.redwood.layout.compose)
            api(libs.redwood.lazylayout.compose)
            api(projects.schema.widget)
        }
    }
}

redwoodSchema {
    source = projects.schema
    type = "com.example.redwood.emojisearch.EmojiSearch"
}
