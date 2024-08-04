package com.parsuomash.telescope.notifier

internal object NotifierManagerImpl {
    private val listeners = mutableListOf<NotifierManager.Listener>()

    fun addListener(listener: NotifierManager.Listener) {
        listeners.add(listener)
    }

    fun onNotificationClicked(data: PayloadData) {
        listeners.forEach { it.onNotificationClicked(data) }
    }
}
