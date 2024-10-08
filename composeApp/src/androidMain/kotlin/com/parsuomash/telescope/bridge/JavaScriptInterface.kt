package com.parsuomash.telescope.bridge

import android.webkit.JavascriptInterface

class JavaScriptInterface(
    private val pushRouteJSCallback: (route: String) -> Unit,
    private val popRouteJSCallback: () -> Unit,
    private val pushRouteAndroidCallback: (route: String) -> Unit,
    private val popRouteAndroidCallback: () -> Unit,
    private val hideBottomSheetCallback: () -> Unit,
    private val finishActivityCallback: () -> Unit,
    private val showBottomSheetCallback: () -> Unit,
    private val nationalCode: String,
) {
    @JavascriptInterface
    fun getNationalCode(): String = nationalCode

    @JavascriptInterface
    fun pushRouteJS(route: String) {
        pushRouteJSCallback(route)
    }

    @JavascriptInterface
    fun popRouteJS() {
        popRouteJSCallback()
    }

    @JavascriptInterface
    fun pushRouteAndroid(route: String) {
        pushRouteAndroidCallback(route)
    }

    @JavascriptInterface
    fun popRouteAndroid() {
        popRouteAndroidCallback()
    }

    @JavascriptInterface
    fun hideBottomSheet() {
        hideBottomSheetCallback()
    }

    @JavascriptInterface
    fun showBottomSheet() {
        showBottomSheetCallback()
    }

    @JavascriptInterface
    fun finishActivity() {
        finishActivityCallback()
    }
}
