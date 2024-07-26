package com.parsuomash.telescope.compose.notifier

import androidx.compose.runtime.Composable

internal interface Notifier {
    fun notify(title: String, message: String)
}

@Composable
internal expect fun rememberNotifier(): Notifier
