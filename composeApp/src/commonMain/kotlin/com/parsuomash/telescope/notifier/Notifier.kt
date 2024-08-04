package com.parsuomash.telescope.notifier

import androidx.compose.runtime.Composable

/**
 * Class that represent local notification
 */
internal interface Notifier {
    /**
     * Sends local notification to device
     * @param title Title part
     * @param body Body part
     * @param payloadData Extra payload data information.
     * @return notification id
     */
    fun notify(title: String, message: String, payloadData: Map<String, String> = mapOf()): Int

    /**
     * Sends local notification to device with id
     * @param id notification id
     * @param title Title part
     * @param body Body part
     * @param payloadData Extra payload data information
     */
    fun notify(id: Int, title: String, message: String, payloadData: Map<String, String> = mapOf()) {}

    /**
     * Remove notification by id
     * @param id notification id
     */
    fun remove(id: Int) {}

    /**
     * Removes all previously shown notifications
     * @see remove(id) for removing specific notification.
     */
    fun removeAll() {}
}

@Composable
internal expect fun rememberNotifier(): Notifier
