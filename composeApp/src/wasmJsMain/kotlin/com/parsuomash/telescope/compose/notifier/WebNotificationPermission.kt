package com.parsuomash.telescope.compose.notifier

import org.w3c.notifications.NotificationPermission as WNotificationPermission
import org.w3c.notifications.GRANTED
import org.w3c.notifications.Notification

internal class WebNotificationPermission : NotificationPermission {
    override fun hasNotificationPermission(onPermissionResult: (Boolean) -> Unit) {
        val permission = Notification.permission
        onPermissionResult(permission == WNotificationPermission.GRANTED)
    }

    override fun askNotificationPermission(onPermissionGranted: (Boolean) -> Unit) {
        Notification.requestPermission().then {
            onPermissionGranted(false)
            null
        }
    }
}
