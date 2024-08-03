package com.parsuomash.telescope.compose.notifier

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.parsuomash.telescope.compose.notifier.permission.AndroidNotificationPermissionChecker
import com.parsuomash.telescope.core.extensions.ACTION_NOTIFICATION_CLICK
import com.parsuomash.telescope.core.extensions.notificationManager
import kotlin.random.Random

@Composable
internal actual fun rememberNotifier(configuration: NotificationPlatformConfiguration): Notifier {
    val context = LocalContext.current
    val notificationPlatformConfiguration = remember {
        (configuration as NotificationPlatformConfiguration.Android)
    }
    val notificationPermission: NotificationPermissionChecker = remember {
        AndroidNotificationPermissionChecker(context)
    }
    val notificationChannelFactory: NotificationChannelFactory = remember {
        NotificationChannelFactory(
            context = context,
            channelData = notificationPlatformConfiguration.notificationChannelData
        )
    }

    return remember {
        object : Notifier {
            override fun notify(title: String, message: String, payloadData: Map<String, String>): Int {
                val notificationID = Random.nextInt(0, Int.MAX_VALUE)
                notify(notificationID, title, message, payloadData)
                return notificationID
            }

            override fun notify(id: Int, title: String, message: String, payloadData: Map<String, String>) {
                notificationPermission.hasNotificationPermission {
                    if (it.not())
                        Log.w(
                            "AndroidNotifier", "You need to ask runtime " +
                                "notification permission (Manifest.permission.POST_NOTIFICATIONS) in your activity"
                        )
                }
                val notificationManager = context.notificationManager ?: return
                val pendingIntent = getPendingIntent(payloadData)
                notificationChannelFactory.createChannels()
                val notification = NotificationCompat.Builder(
                    context,
                    notificationPlatformConfiguration.notificationChannelData.id
                ).apply {
                    setChannelId(notificationPlatformConfiguration.notificationChannelData.id)
                    setContentTitle(title)
                    setContentText(message)
                    setSmallIcon(notificationPlatformConfiguration.notificationIconResId)
                    setAutoCancel(true)
                    setContentIntent(pendingIntent)
                    notificationPlatformConfiguration.notificationIconColorResId?.let {
                        color = ContextCompat.getColor(context, it)
                    }
                }.build()

                notificationManager.notify(id, notification)
            }

            override fun remove(id: Int) {
                val notificationManager = context.notificationManager ?: return
                notificationManager.cancel(id)
            }

            override fun removeAll() {
                val notificationManager = context.notificationManager ?: return
                notificationManager.cancelAll()
            }

            private fun getPendingIntent(payloadData: Map<String, String>): PendingIntent? {
                val intent = getLauncherActivityIntent()?.apply {
                    putExtra(ACTION_NOTIFICATION_CLICK, ACTION_NOTIFICATION_CLICK)
                    payloadData.forEach { putExtra(it.key, it.value) }
                }
                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

                return PendingIntent.getActivity(context, 0, intent, flags)
            }

            private fun getLauncherActivityIntent(): Intent? {
                val packageManager = context.applicationContext.packageManager
                return packageManager.getLaunchIntentForPackage(context.applicationContext.packageName)
            }
        }
    }
}
