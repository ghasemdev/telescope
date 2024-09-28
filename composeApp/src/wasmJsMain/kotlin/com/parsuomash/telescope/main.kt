package com.parsuomash.telescope

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import com.parsuomash.telescope.di.TelescopeKoinContext
import com.parsuomash.telescope.notifier.NotificationConfiguration
import com.parsuomash.telescope.notifier.ProvideNotificationConfiguration
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    TelescopeKoinContext.start()

    ComposeViewport(document.body!!) {
        val notificationConfiguration = remember {
            NotificationConfiguration.Web(notificationIconPath = "telescope.png")
        }

        DisposableEffect(Unit) {
            onDispose {
                TelescopeKoinContext.stop()
            }
        }

        ProvideNotificationConfiguration(notificationConfiguration) {
            Box(Modifier.fillMaxSize().background(Color.Blue)) {
                Box(Modifier.fillMaxSize().padding(100.dp).background(Color.Red)) {
                    Box(Modifier.fillMaxSize().padding(100.dp).background(Color.Blue)) {
                        Box(Modifier.fillMaxSize().padding(100.dp).background(Color.Red)) {
                            Box(Modifier.fillMaxSize().padding(100.dp).background(Color.Blue)) {
                            }
                        }
                    }
                }
            }
        }
    }
}
