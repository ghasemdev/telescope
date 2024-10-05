package com.parsuomash.telescope.bridge

import android.webkit.JavascriptInterface

class JavaScriptInterface(
    private val addRouteCallback: (route: String) -> Unit,
    private val removeRouteCallback: (route: String) -> Unit,
    private val finishActivityCallback: () -> Unit,
    private val nationalCode: String,
) {
    @JavascriptInterface
    fun getNationalCode(): String = nationalCode

    @JavascriptInterface
    fun addRoute(route: String) {
        addRouteCallback(route)
    }

    @JavascriptInterface
    fun removeRoute(route: String) {
        removeRouteCallback(route)
    }

    @JavascriptInterface
    fun finishActivity() {
        finishActivityCallback()
    }
}
