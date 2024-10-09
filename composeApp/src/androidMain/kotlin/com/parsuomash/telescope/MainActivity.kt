package com.parsuomash.telescope

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.parsuomash.telescope.bridge.JavaScriptInterface
import com.parsuomash.telescope.di.TelescopeKoinContext
import com.parsuomash.telescope.di.scope.ActivityScope
import com.parsuomash.telescope.notifier.NotificationConfiguration
import com.parsuomash.telescope.notifier.NotifierManager
import com.parsuomash.telescope.notifier.ProvideNotificationConfiguration
import com.parsuomash.telescope.notifier.extensions.onCreateOrOnNewIntent
import com.parsuomash.telescope.notifier.permission.notificationPermissionRequester
import org.koin.android.ext.koin.androidContext

class MainActivity : ActivityScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        TelescopeKoinContext.start {
            androidContext(applicationContext)
        }

        val notificationPermission by notificationPermissionRequester()
        notificationPermission.askNotificationPermission()

        NotifierManager.onCreateOrOnNewIntent(intent)

        setContent {
            val notificationConfiguration = remember {
                NotificationConfiguration.Android(
                    notificationIconResId = R.drawable.ic_telescope,
                    notificationIconColorResId = R.color.notification_icon
                )
            }

            ProvideNotificationConfiguration(notificationConfiguration) {
                WebViewScreen(url = "http://172.30.230.147:8080/")
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    fun WebViewScreen(url: String) {
        val context = LocalContext.current
        var isLoading: Boolean by remember { mutableStateOf(false) }
        var isWebViewBottomSheetOpen: Boolean by remember { mutableStateOf(false) }
        val webViewRoutes = remember { mutableStateListOf<String>() }
        val webView = remember {
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        isLoading = true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        isLoading = false
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        Toast.makeText(context, "${error?.description}", Toast.LENGTH_SHORT).show()
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean = false
                }

                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.domStorageEnabled = true

                // Add a JavaScript interface to allow communication between JavaScript and Kotlin
                addJavascriptInterface(
                    JavaScriptInterface(
                        nationalCode = "0925277800",
                        finishActivityCallback = { finish() },
                        pushRouteJSCallback = { webViewRoutes.add(it) },
                        popRouteJSCallback = { webViewRoutes.removeAt(webViewRoutes.size - 1) },
                        pushRouteAndroidCallback = {},
                        popRouteAndroidCallback = { finish() },
                        hideBottomSheetCallback = { isWebViewBottomSheetOpen = false },
                        showBottomSheetCallback = { isWebViewBottomSheetOpen = true },
                    ), "JSInterface"
                )
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

        DisposableEffect(Unit) {
            onDispose {
                webView.destroy()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF152132))
                .systemBarsPadding()
        ) {
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0XFF40BED0)
                )
            }
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { webView },
                update = { view ->
                    view.loadUrl(url)
                }
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        NotifierManager.onCreateOrOnNewIntent(intent)
    }

    override fun onDestroy() {
        TelescopeKoinContext.stop()
        super.onDestroy()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
