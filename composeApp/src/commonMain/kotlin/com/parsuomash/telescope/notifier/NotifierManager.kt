package com.parsuomash.telescope.notifier

internal typealias PayloadData = Map<String, *>

internal object NotifierManager {
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
