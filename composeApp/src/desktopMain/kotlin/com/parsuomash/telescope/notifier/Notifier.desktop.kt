package com.parsuomash.telescope.notifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Notification
import com.parsuomash.telescope.notifier.Notifier

@Composable
internal actual fun rememberNotifier(): Notifier {
    val trayState = LocalTrayState.current
    return remember {
        object : Notifier {
            override fun notify(title: String, message: String, payloadData: Map<String, String>): Int {
                val notificationManager = Notification(title = title, message = message)
                trayState.sendNotification(notificationManager)
                return 0
            }
        }
    }
}
