package com.parsuomash.telescope.bridge

import android.webkit.JavascriptInterface

class JavaScriptInterface(
    private val pushRouteCallback: (route: String) -> Unit,
    private val popRouteCallback: () -> Unit,
    private val finishActivityCallback: () -> Unit,
    private val nationalCode: String,
) {
    @JavascriptInterface
    fun getNationalCode(): String = nationalCode

    @JavascriptInterface
    fun pushRoute(route: String) {
        pushRouteCallback(route)
    }

    @JavascriptInterface
    fun popRoute() {
        popRouteCallback()
    }

    @JavascriptInterface
    fun finishActivity() {
        finishActivityCallback()
    }
}
