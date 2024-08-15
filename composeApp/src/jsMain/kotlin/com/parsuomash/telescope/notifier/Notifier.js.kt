package com.parsuomash.telescope.notifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.browser.window
import org.w3c.notifications.Notification
import org.w3c.notifications.NotificationOptions

private fun isNotificationSupported(): Boolean {
    return js("typeof Notification !== 'undefined'").unsafeCast<Boolean>()
}

@Composable
internal actual fun rememberNotifier(): Notifier {
    val notificationConfiguration = LocalNotificationConfiguration.current as NotificationConfiguration.Web
    val notificationPermission: NotificationPermission = remember { WebNotificationPermission() }

    LaunchedEffect(Unit) {
        if (notificationConfiguration.askNotificationPermissionOnStart) {
            notificationPermission.askNotificationPermission()
        }
    }

    return remember {
        object : Notifier {
            override fun notify(title: String, message: String, payloadData: Map<String, String>): Int {
                if (isNotificationSupported().not()) {
                    alertNotification(message)
                    return 0
                }
                notificationPermission.askNotificationPermission {
                    notificationPermission.hasNotificationPermission { hasPermission ->
                        if (hasPermission) {
                            showNotification(title = title, message = message)
                        } else {
                            alertNotification(message)
                        }
                    }
                }
                return 0
            }

            private fun showNotification(title: String, message: String) {
                val options = NotificationOptions(
                    body = message,
                    icon = notificationConfiguration.notificationIconPath
                )
                Notification(title, options)
            }

            private fun alertNotification(message: String) {
                window.alert(message)
            }
        }
    }
}
