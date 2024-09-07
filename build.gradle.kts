import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.zipline) apply false
    alias(libs.plugins.redwood.schema) apply false
    alias(libs.plugins.redwood.generator.compose) apply false
    alias(libs.plugins.redwood.generator.widget) apply false
    alias(libs.plugins.redwood.generator.modifiers) apply false
    alias(libs.plugins.redwood.generator.protocol.guest) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
}

buildscript {
    dependencies {
        classpath(libs.dokka.base)
        classpath(libs.buildkonfig.gradle.plugin)
        classpath("app.cash.redwood.build:gradle-plugin:0.6.0")
        classpath("app.cash.redwood:redwood-gradle-plugin:0.14.0")
    }
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jetbrains.dokka")
    }
    dependencies {
        //noinspection UseTomlInstead
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
        dokkaPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.9.20")
    }
    // Static analysis
    detekt {
        // detekt version
        toolVersion = "1.23.6"
        // preconfigure defaults
        buildUponDefaultConfig = true
        // activate all available (even unstable) rules.
        allRules = false
        // point to your custom config defining rules to run, overwriting default behavior
        @Suppress("DEPRECATION")
        config = files("$rootDir/config/detekt/detekt.yml")
        // a way of suppressing issues before introducing detekt
        baseline = file("$rootDir/config/detekt/baseline.xml")
    }
    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true)
            xml.required.set(false)
            txt.required.set(false)
            sarif.required.set(false)
            md.required.set(true)
        }
    }
}

// Dokka
tasks.dokkaHtml.configure {
    dokkaSourceSets {
        configureEach {
            perPackageOption {
                matchingRegex.set(""".*\.app.*""")
                suppress.set(true)
            }
        }
    }
}
tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(rootDir.resolve("docs"))
    // Set module name displayed in the final output
    moduleName.set("Telescope")
    // Custom Style
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        customAssets = listOf(file("$rootDir/asset/telescope.png"))
    }
}
