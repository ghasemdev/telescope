plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.redwood.generator.compose)
}

kotlin {
    js { browser() }
    jvm()

    sourceSets {
        commonMain.dependencies {
            api("app.cash.redwood:redwood-layout-compose:0.14.0")
            api("app.cash.redwood:redwood-lazylayout-compose:0.14.0")
            api(projects.schema.widget)
        }
    }
}

redwoodSchema {
    source = projects.schema
    type = "com.example.redwood.emojisearch.EmojiSearch"
}
