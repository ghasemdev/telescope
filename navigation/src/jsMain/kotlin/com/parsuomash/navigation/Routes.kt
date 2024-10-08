package com.parsuomash.navigation

import com.parsuomash.navigation.core.tryIgnore

fun pushRouteJS(route: String) {
    tryIgnore { com.parsuomash.navigation.internal.pushRouteJS(route) }
}

fun popRouteJS() {
    tryIgnore { com.parsuomash.navigation.internal.popRouteJS() }
}

fun pushRouteAndroid(route: String) {
    tryIgnore { com.parsuomash.navigation.internal.pushRouteAndroid(route) }
}

fun popRouteAndroid() {
    tryIgnore { com.parsuomash.navigation.internal.popRouteAndroid() }
}

fun hideBottomSheet() {
    tryIgnore { com.parsuomash.navigation.internal.hideBottomSheet() }
}

fun showBottomSheet() {
    tryIgnore { com.parsuomash.navigation.internal.showBottomSheet() }
}
