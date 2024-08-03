package com.parsuomash.telescope.compose.notifier

internal object NotifierManagerImpl {
    private val listeners = mutableListOf<NotifierManager.Listener>()

    fun initialize(configuration: NotificationPlatformConfiguration) {
    }

    fun addListener(listener: NotifierManager.Listener) {
        listeners.add(listener)
    }

    fun onNotificationClicked(data: PayloadData) {
        println("Notification is clicked")
        if (listeners.size == 0) println("There is no listener to notify onPushPayloadData")
        listeners.forEach { it.onNotificationClicked(data) }
    }
}
