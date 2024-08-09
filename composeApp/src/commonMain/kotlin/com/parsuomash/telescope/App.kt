package com.parsuomash.telescope

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.parsuomash.telescope.di.TelescopeKoinContext
import com.parsuomash.telescope.presentation.MoviesScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinIsolatedContext

@Composable
@Preview
fun App() {
    KoinIsolatedContext(TelescopeKoinContext.app) {
        MaterialTheme {
            MoviesScreen()
        }
    }
}
