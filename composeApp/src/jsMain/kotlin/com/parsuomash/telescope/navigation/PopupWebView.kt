package com.parsuomash.telescope.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.LocalNavigator
import com.parsuomash.navigation.popRoute

private var routesCount by mutableIntStateOf(0)

@Composable
fun ObservePopRoute() {
    val navigation = LocalNavigator.current
    LaunchedEffect(routesCount) {
        if (routesCount > 0) {
            navigation?.pop()
            popRoute()
            routesCount--
        }
    }
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun popWebViewRoute() {
    routesCount++
}
