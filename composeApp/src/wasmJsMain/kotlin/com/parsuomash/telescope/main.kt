package com.parsuomash.telescope

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.parsuomash.telescope.di.TelescopeKoinContext
import com.parsuomash.telescope.notifier.NotificationConfiguration
import com.parsuomash.telescope.notifier.ProvideNotificationConfiguration
import kotlinx.browser.document
import org.koin.compose.KoinIsolatedContext

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val notificationConfiguration = remember {
            NotificationConfiguration.Web(notificationIconPath = "telescope.png")
        }

        DisposableEffect(Unit) {
            TelescopeKoinContext.start()

            onDispose {
                TelescopeKoinContext.stop()
            }
        }

        KoinIsolatedContext(TelescopeKoinContext.app) {
            ProvideNotificationConfiguration(notificationConfiguration) {
                App()
            }
        }
    }
}
