package com.parsuomash.telescope.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey

class BiometricScreen : Screen {
    override val key: ScreenKey
        get() = uniqueScreenKey

    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF152132)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
            }
        }
    }
}
