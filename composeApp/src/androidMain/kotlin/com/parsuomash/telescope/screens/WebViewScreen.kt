package com.parsuomash.telescope.screens

import android.annotation.SuppressLint
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.parsuomash.telescope.bridge.JavaScriptInterface

@SuppressLint("StaticFieldLeak")
private var _webView: WebView? = null

class WebViewScreen : Screen {
    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow

        var value by rememberSaveable { mutableIntStateOf(0) }

        var isLoading: Boolean by rememberSaveable { mutableStateOf(true) }
        var isWebViewBottomSheetOpen: Boolean by rememberSaveable { mutableStateOf(false) }
        val webViewRoutes = remember { mutableStateListOf<String>() }
        val webView = remember {
            _webView ?: WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        isLoading = false
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        Toast.makeText(context, "${error?.description}", Toast.LENGTH_SHORT).show()
                    }
                }

                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.domStorageEnabled = true

                // Add a JavaScript interface to allow communication between JavaScript and Kotlin
                addJavascriptInterface(
                    JavaScriptInterface(
                        nationalCode = "0925277800",
                        finishActivityCallback = { },
                        pushRouteJSCallback = { webViewRoutes.add(it) },
                        popRouteJSCallback = { webViewRoutes.removeAt(webViewRoutes.size - 1) },
                        pushRouteAndroidCallback = { navigator.push(BiometricScreen()) },
                        popRouteAndroidCallback = { navigator.pop() },
                        hideBottomSheetCallback = { isWebViewBottomSheetOpen = false },
                        showBottomSheetCallback = { isWebViewBottomSheetOpen = true },
                    ), "JSInterface"
                )
                setBackgroundColor(Color(0xFF152132).toArgb())
                loadUrl("http://172.30.230.147:8080/")

                _webView = this
            }
        }

        // Navigate only there is no screen in web
        BackHandler(webViewRoutes.size > 0 && !isWebViewBottomSheetOpen) {
            webView.evaluateJavascript(
                "javascript:composeApp.com.parsuomash.telescope.navigation.popWebViewRoute()"
            ) {}
        }
        BackHandler(isWebViewBottomSheetOpen) {
            webView.evaluateJavascript(
                "javascript:composeApp.com.parsuomash.telescope.navigation.closeWebViewBottomSheet()"
            ) {}
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF152132))
        ) {
            Column {
                Button(onClick = {
                    value++
                }) { }
                Text(
                    text = value.toString(),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { webView },
                    )
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color(0XFF40BED0)
                        )
                    }
                }
            }
        }
    }
}
