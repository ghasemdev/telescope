plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.redwood.schema)
}

dependencies {
    api(libs.redwood.layout.schema)
    api(libs.redwood.lazylayout.schema)
    api(projects.values)
}

redwoodSchema {
    type = "com.example.redwood.emojisearch.EmojiSearch"
}
