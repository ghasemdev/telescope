@file:JsFileName("test")

package com.parsuomash.telescope.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

private var popupSignal: Int by mutableIntStateOf(0)

@Composable
fun ObserveToPopup() {
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(popupSignal) {
        if (popupSignal != 0) {
            navigator.pop()
        }
    }
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun popupSignal() {
    js("console.log('FUCKCKKCKCKCKCKCK')")
}
