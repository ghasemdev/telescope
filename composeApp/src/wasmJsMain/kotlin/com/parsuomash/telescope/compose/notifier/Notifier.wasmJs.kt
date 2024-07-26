package com.parsuomash.telescope.compose.notifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.browser.window
import org.w3c.notifications.Notification
import org.w3c.notifications.NotificationOptions

@JsFun("() => typeof Notification !== 'undefined'")
private external fun isNotificationSupported(): Boolean

@Composable
internal actual fun rememberNotifier(): Notifier {
    val notificationPermission = remember { WebNotificationPermission() }
    return remember {
        object : Notifier {
            override fun notify(title: String, message: String) {
                if (isNotificationSupported().not()) {
                    alertNotification(message)
                    return
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
            }
        }
    }
}

private fun showNotification(title: String, message: String) {
    val options = NotificationOptions(body = message)
    Notification(title, options)
}

private fun alertNotification(message: String) {
    window.alert(message)
}
