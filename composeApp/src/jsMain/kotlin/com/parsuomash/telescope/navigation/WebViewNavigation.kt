@file:OptIn(ExperimentalJsExport::class)

package com.parsuomash.telescope.navigation

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.LocalNavigator
import com.parsuomash.navigation.hideBottomSheet
import com.parsuomash.navigation.popRouteJS
import com.parsuomash.navigation.showBottomSheet

private var routesCount by mutableIntStateOf(0)
private var bottomSheetCount by mutableIntStateOf(0)

@Composable
fun ObservePopRoute() {
    val navigation = LocalNavigator.current
    LaunchedEffect(routesCount) {
        if (routesCount > 0) {
            navigation?.pop()
            popRouteJS()
            routesCount--
        }
    }
}

@Composable
fun ObserveIsBottomSheetClosed(state: ModalBottomSheetState) {
    LaunchedEffect(bottomSheetCount) {
        if (bottomSheetCount > 0) {
            state.hide()
            bottomSheetCount--
        }
    }

    LaunchedEffect(state.isVisible) {
        if (state.isVisible) {
            showBottomSheet()
        } else {
            hideBottomSheet()
        }
    }
}

@JsExport
fun popWebViewRoute() {
    routesCount++
}

@JsExport
fun closeWebViewBottomSheet() {
    bottomSheetCount++
}
