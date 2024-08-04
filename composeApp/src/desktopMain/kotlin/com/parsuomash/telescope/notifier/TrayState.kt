package com.parsuomash.telescope.notifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.window.TrayState

internal val LocalTrayState = compositionLocalOf<TrayState> {
    error("TrayState must be provide!")
}

@Composable
internal fun ProvideTrayState(trayState: TrayState, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalTrayState provides trayState) {
        content()
    }
}
