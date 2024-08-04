package com.parsuomash.telescope

import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.parsuomash.telescope.notifier.NotificationConfiguration
import com.parsuomash.telescope.notifier.ProvideNotificationConfiguration
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val notificationConfiguration = remember {
            NotificationConfiguration.Web(notificationIconPath = "telescope.png")
        }

        ProvideNotificationConfiguration(notificationConfiguration) {
            App()
        }
    }
}
