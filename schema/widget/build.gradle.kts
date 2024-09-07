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
            api("app.cash.redwood:redwood-layout-widget:0.14.0")
            api("app.cash.redwood:redwood-lazylayout-widget:0.14.0")
            api(projects.schema.modifiers)
            api(projects.values)
        }
    }
}

redwoodSchema {
    source = projects.schema
    type = "com.example.redwood.emojisearch.EmojiSearch"
}
