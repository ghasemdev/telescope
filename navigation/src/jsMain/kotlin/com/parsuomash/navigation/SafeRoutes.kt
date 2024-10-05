package com.parsuomash.navigation

import com.parsuomash.navigation.core.tryIgnore

fun safeAddRoute(route: String) {
    tryIgnore { addRoute(route) }
}

fun safeRemoveRoute(route: String) {
    tryIgnore { removeRoute(route) }
}
