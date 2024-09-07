plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.redwood.schema)
}

dependencies {
    api("app.cash.redwood:redwood-layout-schema:0.14.0")
    api("app.cash.redwood:redwood-lazylayout-schema:0.14.0")
    api(projects.values)
}

redwoodSchema {
    type = "com.example.redwood.emojisearch.EmojiSearch"
}
