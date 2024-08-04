package com.parsuomash.telescope.notifier.permission

import android.Manifest
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.parsuomash.telescope.notifier.NotificationPermissionRequester
import com.parsuomash.telescope.notifier.extensions.hasPermission

/**
 * in Activity
 *
 * ```kotlin
 * private val notificationPermission by notificationPermission()
 * ```
 *
 * then #onCreate method
 *
 * ```kotlin
 * notificationPermission.askNotificationPermission {
 *  println("HasNotification Permission: $it")
 * }
 * ```
 */
internal fun ComponentActivity.notificationPermissionRequester(): Lazy<AndroidNotificationPermissionRequester> =
    lazy(LazyThreadSafetyMode.NONE) {
        AndroidNotificationPermissionRequester(this)
    }

/**
 * Android notification utility class for making it easier to ask permission from user.
 */
internal class AndroidNotificationPermissionRequester(
    private val activity: ComponentActivity
) : NotificationPermissionRequester {
    private var mOnResult: ((Boolean) -> Unit)? = null

    private val requestPermissionLauncher = activity
        .registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            mOnResult?.invoke(isGranted)
        }

    /**
     * Asks notification permission from user
     * @param onPermissionGranted lambda is called when notification permission is returned
     */
    override fun askNotificationPermission(onPermissionGranted: (Boolean) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            askIfNotHasPermission(permission = Manifest.permission.POST_NOTIFICATIONS, onResult = onPermissionGranted)
        } else onPermissionGranted(true)
    }

    private fun askIfNotHasPermission(permission: String, onResult: (Boolean) -> Unit = {}) {
        if (activity.hasPermission(permission)) {
            onResult(true)
        } else {
            mOnResult = onResult
            requestPermissionLauncher.launch(permission)
        }
    }
}
