package com.parsuomash.telescope.compose.notifier

internal typealias PayloadData = Map<String, *>

internal object NotifierManager {
    /**
     * Call initialize function on Application Start.
     * @param configuration pass either ios or android configuration depending on platform
     * @see NotificationPlatformConfiguration.Ios
     * @see NotificationPlatformConfiguration.Android
     */
    fun initialize(configuration: NotificationPlatformConfiguration) {
        NotifierManagerImpl.initialize(configuration)
    }

    /**
     * For listening updates such as push notification token changes
     */
    fun addListener(listener: Listener) {
        NotifierManagerImpl.addListener(listener)
    }

    interface Listener {
        /**
         * Called when notification is clicked
         * @param data Push Notification Payload Data
         */
        fun onNotificationClicked(data: PayloadData) {}
    }
}
