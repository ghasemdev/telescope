package com.parsuomash.telescope

import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.window.ComposeUIViewController
import com.parsuomash.telescope.di.TelescopeKoinContext

fun MainViewController() = ComposeUIViewController(
    configure = {
        TelescopeKoinContext.start()
    }
) {
    DisposableEffect(Unit) {
        onDispose {
            TelescopeKoinContext.stop()
        }
    }

    App()
}
