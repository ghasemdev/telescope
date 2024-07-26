package com.parsuomash.telescope.compose.notifier

internal interface NotificationPermission {
    fun hasNotificationPermission(onPermissionResult: (Boolean) -> Unit)
    fun askNotificationPermission(onPermissionGranted: () -> Unit)
}
