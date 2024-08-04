package com.parsuomash.telescope.notifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.parsuomash.telescope.notifier.NotificationConfiguration.Android.NotificationChannelData

/**
 * You can configure some customization for notifications depending on the platform
 */
internal sealed interface NotificationConfiguration {
    /**
     * Android Notification Customization. Create this object in android source.
     *
     * @param  notificationIconResId icon ResourceId (R.drawable.ic_notification)
     * @param notificationIconColorResId optional icon color ResourceId (R.color.yellow)
     * @param notificationChannelData optional notification channel data for General or Miscellaneous notifications
     * @see NotificationChannelData
     */
    class Android(
        val notificationIconResId: Int,
        val notificationIconColorResId: Int? = null,
        val notificationChannelData: NotificationChannelData = NotificationChannelData()
    ) : NotificationConfiguration {
        /**
         * By default Notification channel with below configuration is created but you can change it
         * @param id for General(or Miscellaneous or Other) notifications. Default value: "DEFAULT_NOTIFICATION_CHANNEL_ID"
         * @param name this is the title that is shown on app notification channels. Default value is "General"
         * Usually it is either General or Miscellaneous or Miscellaneous in most apps
         * @param description Notification description
         */
        class NotificationChannelData(
            val id: String = "DEFAULT_NOTIFICATION_CHANNEL_ID",
            val name: String = "General",
            val description: String = "",
        )
    }

    /**
     * Ios notification customization.
     *
     * @param askNotificationPermissionOnStart Default value is true, when library is initialized it
     * will ask notification permission automatically from the user.
     * By setting askNotificationPermissionOnStart false, you can customize to ask permission whenever you want.
     */
    data class Ios(
        val askNotificationPermissionOnStart: Boolean = true
    ) : NotificationConfiguration

    /**
     * Web notification customization.
     *
     * @param askNotificationPermissionOnStart Default value is true, when library is initialized it
     * will ask notification permission automatically from the user.
     * By setting askNotificationPermissionOnStart false, you can customize to ask permission whenever you want.
     *
     *@param  notificationIconPath Notification icon path
     */
    class Web(
        val askNotificationPermissionOnStart: Boolean = true,
        val notificationIconPath: String? = null
    ) : NotificationConfiguration
}

internal val LocalNotificationConfiguration = compositionLocalOf<NotificationConfiguration> {
    error("NotificationPlatformConfiguration must be provide!")
}

@Composable
internal fun ProvideNotificationConfiguration(
    configuration: NotificationConfiguration,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalNotificationConfiguration provides configuration) {
        content()
    }
}
