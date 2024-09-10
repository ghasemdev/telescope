plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.redwood.generator.protocol.guest)
}

kotlin {
    js { browser() }

    sourceSets {
        commonMain.dependencies {
            api(projects.schema.widget)
        }
    }
}

redwoodSchema {
    source = projects.schema
    type = "com.parsumash.schema.EmojiSearch"
}
