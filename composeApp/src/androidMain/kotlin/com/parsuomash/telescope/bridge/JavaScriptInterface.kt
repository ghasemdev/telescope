package com.parsuomash.telescope.bridge

import android.webkit.JavascriptInterface

class JavaScriptInterface(
    private val toastCallback: (message: String) -> Unit,
    private val nationalCode: String,
) {
    @JavascriptInterface
    fun toast(message: String) {
        toastCallback(message)
    }

    @JavascriptInterface
    fun getNationalCode(): String = nationalCode
}
