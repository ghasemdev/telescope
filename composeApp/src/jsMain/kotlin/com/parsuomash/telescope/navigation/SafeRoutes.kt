package com.parsuomash.telescope.navigation

import com.parsuomash.telescope.core.tryIgnore

fun safeAddRoute(route: String) {
    tryIgnore { addRoute(route) }
}

fun safeRemoveRoute(route: String) {
    tryIgnore { removeRoute(route) }
}
