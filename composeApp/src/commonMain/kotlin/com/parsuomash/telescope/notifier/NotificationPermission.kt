package com.parsuomash.telescope.notifier

internal interface NotificationPermissionChecker {
    fun hasNotificationPermission(onPermissionResult: (Boolean) -> Unit)
}

internal interface NotificationPermissionRequester {
    fun askNotificationPermission(onPermissionGranted: (Boolean) -> Unit = {})
}

internal interface NotificationPermission : NotificationPermissionChecker, NotificationPermissionRequester
