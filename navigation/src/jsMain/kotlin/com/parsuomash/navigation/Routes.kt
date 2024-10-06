package com.parsuomash.navigation

import com.parsuomash.navigation.core.tryIgnore

fun pushRoute(route: String) {
    tryIgnore { pushRouteJS(route) }
}

fun popRoute() {
    tryIgnore { popRouteJS() }
}
