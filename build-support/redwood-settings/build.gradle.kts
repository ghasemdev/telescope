plugins {
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("redwoodSettings") {
            id = "app.cash.redwood.settings"
            displayName = "Redwood settings plugin"
            description = "Gradle plugin for Redwood build settings"
            implementationClass = "app.cash.redwood.buildsettings.RedwoodSettingsPlugin"
        }
    }
}
