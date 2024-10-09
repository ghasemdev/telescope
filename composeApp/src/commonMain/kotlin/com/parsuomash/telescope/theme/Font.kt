package com.parsuomash.telescope.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.font.FontFamily

internal val LocalFontFamily = compositionLocalOf<FontFamily> {
    error("FontFamily must be provide!")
}

@Composable
internal fun ProvideFontFamily(
    fontFamily: FontFamily,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalFontFamily provides fontFamily) {
        content()
    }
}
