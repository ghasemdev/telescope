import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)

    id("com.codingfeline.buildkonfig")
}

buildkonfig {
    packageName = "com.parsuomash.telescope"
    defaultConfigs {
        buildConfigField(type = BOOLEAN, name = "isDevelope", value = "false", const = true)
    }
    defaultConfigs("dev") {
        buildConfigField(type = BOOLEAN, name = "isDevelope", value = "true", const = true)
    }
}

kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(project.projectDir.path)
//                    }
//                }
//            }
//        }
//        binaries.executable()
//    }

    js(IR) {
        useEsModules()
        browser {
            commonWebpackConfig {
                outputFileName = "app.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        add(project.projectDir.path)
                    }
                }
            }
            useCommonJs()
        }
        binaries.executable()
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.bundles.koin)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.kotlinx)
            implementation(libs.bundles.lifecycle)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.atomicfu)
            implementation(libs.navigation.compose)
            implementation(libs.napier)
            implementation(libs.sqldelight.coroutines)

            implementation(project(":gameservice-shared"))
            implementation(libs.zipline)
        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.annotation)
            implementation(libs.slf4j.nop)
            implementation(libs.sqldelight.android.driver)
            implementation(libs.androidx.startup.runtime)

            implementation(libs.zipline.loader)
            implementation(libs.zipline.profiler)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.androidx.annotation)
            implementation(libs.sqldelight.native.driver)

            implementation(libs.zipline.loader)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.annotation)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.slf4j.nop)
            implementation(libs.sqldelight.sqlite.driver)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(npm("@js-joda/timezone", "2.3.0"))

            implementation(libs.sqldelight.web.driver)
            implementation(npm("sql.js", "1.10.3"))
            implementation(npm("@cashapp/sqldelight-sqljs-worker", libs.versions.sqldelight.get()))
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
        }
//        wasmJsMain.dependencies {
//            implementation(libs.ktor.client.js)
//            implementation(npm("@js-joda/timezone", "2.3.0"))
//        }
    }
}

sqldelight {
    databases {
        create("TelescopeDB") {
            packageName.set("com.parsuomash.telescope")
            generateAsync.set(true)
        }
    }
}

android {
    namespace = "com.parsuomash.telescope"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.parsuomash.telescope"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "assemble/proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
        debugImplementation(libs.leakcanary.android)
        add(PLUGIN_CLASSPATH_CONFIGURATION_NAME, "app.cash.zipline:zipline-kotlin-plugin:1.17.0")
    }
}

compose.desktop {
    application {
        mainClass = "com.parsuomash.telescope.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.parsuomash.telescope"
            packageVersion = "1.0.0"
        }

        buildTypes.release.proguard {
            obfuscate.set(true)
            configurationFiles.from(project.file("assemble/proguard-rules.pro"))
        }
    }
}
