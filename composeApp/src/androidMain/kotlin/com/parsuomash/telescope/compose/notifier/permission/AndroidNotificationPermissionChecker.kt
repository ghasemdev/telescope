package com.parsuomash.telescope.compose.notifier.permission

import android.content.Context
import com.parsuomash.telescope.compose.notifier.NotificationPermissionChecker
import com.parsuomash.telescope.core.extensions.hasNotificationPermission

/**
 * This class is only for checking notification permission,
 * for asking runtime permission use AndroidPermissionUtil in your activity.
 */
internal class AndroidNotificationPermissionChecker(
    private val context: Context
) : NotificationPermissionChecker {
    override fun hasNotificationPermission(onPermissionResult: (Boolean) -> Unit) {
        return onPermissionResult(context.hasNotificationPermission())
    }
}
