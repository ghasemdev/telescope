package com.parsuomash.telescope.core.extensions

import android.content.Intent
import androidx.core.bundle.bundleOf
import com.parsuomash.telescope.compose.notifier.NotifierManager
import com.parsuomash.telescope.compose.notifier.NotifierManagerImpl

/**
 * In order to receive notification data payload this functions needs to be called in
 * Android side in launcher Activity #onCreate and #onNewIntent methods.
 *
 * Example:
 *
 * ```kotlin
 * class MainActivity : ComponentActivity() {
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         Notifier.onCreateOrOnNewIntent(intent)
 *         setContent {
 *             App()
 *         }
 *     }
 *
 *     override fun onNewIntent(intent: Intent?) {
 *         super.onNewIntent(intent)
 *         Notifier.onCreateOrOnNewIntent(intent)
 *     }
 * }
 * ```
 */
internal fun NotifierManager.onCreateOrOnNewIntent(intent: Intent?) {
    if (intent == null) return
    val extras = intent.extras ?: bundleOf()
    val payloadData = mutableMapOf<String, Any>()

    val isNotificationClicked =
        extras.containsKey(ACTION_NOTIFICATION_CLICK) || payloadData.containsKey(ACTION_NOTIFICATION_CLICK)

    extras.keySet().forEach { key ->
        @Suppress("DEPRECATION") val value = extras.get(key)
        value?.let { payloadData[key] = it }
    }

    if (isNotificationClicked) {
        NotifierManagerImpl.onNotificationClicked(payloadData.minus(ACTION_NOTIFICATION_CLICK))
    }
}

internal const val ACTION_NOTIFICATION_CLICK = "com.parsuomash.telescope.notification.ACTION_NOTIFICATION_CLICK"
