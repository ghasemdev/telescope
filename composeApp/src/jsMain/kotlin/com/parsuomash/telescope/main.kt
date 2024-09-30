package com.parsuomash.telescope

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.CanvasBasedWindow
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.parsuomash.telescope.di.TelescopeKoinContext
import com.parsuomash.telescope.notifier.NotificationConfiguration
import com.parsuomash.telescope.notifier.ProvideNotificationConfiguration
import com.parsuomash.telescope.screens.RegisterScreen
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
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Navigator(RegisterScreen()) { navigator ->
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }
}
