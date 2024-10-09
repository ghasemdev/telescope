package com.parsuomash.navigation

import com.parsuomash.navigation.core.tryIgnore
import com.parsuomash.navigation.internal.internalHideBottomSheet
import com.parsuomash.navigation.internal.internalPopRouteAndroid
import com.parsuomash.navigation.internal.internalPopRouteJS
import com.parsuomash.navigation.internal.internalPushRouteAndroid
import com.parsuomash.navigation.internal.internalPushRouteJS
import com.parsuomash.navigation.internal.internalShowBottomSheet

fun pushRouteJS(route: String) {
    tryIgnore { internalPushRouteJS(route) }
}

fun popRouteJS() {
    tryIgnore { internalPopRouteJS() }
}

fun pushRouteAndroid(route: String) {
    tryIgnore { internalPushRouteAndroid(route) }
}

fun popRouteAndroid() {
    tryIgnore { internalPopRouteAndroid() }
}

fun hideBottomSheet() {
    tryIgnore { internalHideBottomSheet() }
}

fun showBottomSheet() {
    tryIgnore { internalShowBottomSheet() }
}
