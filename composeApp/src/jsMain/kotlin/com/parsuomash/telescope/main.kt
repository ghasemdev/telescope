package com.parsuomash.telescope

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.parsuomash.telescope.di.TelescopeKoinContext
import com.parsuomash.telescope.notifier.NotificationConfiguration
import com.parsuomash.telescope.notifier.ProvideNotificationConfiguration
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    TelescopeKoinContext.start()

    onWasmReady {
        CanvasBasedWindow(canvasElementId = "appTarget") {
            val notificationConfiguration = remember {
                NotificationConfiguration.Web(notificationIconPath = "telescope.png")
            }

            DisposableEffect(Unit) {
                onDispose {
                    TelescopeKoinContext.stop()
                }
            }

            ProvideNotificationConfiguration(notificationConfiguration) {
                App()
            }
        }
    }
}
