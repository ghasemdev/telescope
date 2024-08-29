package com.parsuomash.telescope

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import app.cash.zipline.Zipline
import app.cash.zipline.loader.DefaultFreshnessCheckerNotFresh
import app.cash.zipline.loader.LoadResult
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.ZiplineLoader
import com.parsumash.gameservice.GameService
import com.parsuomash.telescope.di.TelescopeKoinContext
import com.parsuomash.telescope.di.scope.ActivityScope
import com.parsuomash.telescope.notifier.NotificationConfiguration
import com.parsuomash.telescope.notifier.NotifierManager
import com.parsuomash.telescope.notifier.ProvideNotificationConfiguration
import com.parsuomash.telescope.notifier.extensions.onCreateOrOnNewIntent
import com.parsuomash.telescope.notifier.permission.notificationPermissionRequester
import java.util.concurrent.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext

class MainActivity : ActivityScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val executorService = Executors.newFixedThreadPool(1) {
            Thread(it, "Zipline")
        }
        val dispatcher = executorService.asCoroutineDispatcher()
        try {
            runBlocking(dispatcher) {
                val zipline = launchZipline(dispatcher)
                val gameService = getGameService(zipline)
                Toast.makeText(this@MainActivity, gameService.calculate(), Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
                App()
            }
        }
    }

    fun getGameService(zipline: Zipline): GameService {
        return zipline.take("gameService")
    }

    suspend fun launchZipline(dispatcher: CoroutineDispatcher): Zipline {
        val manifestUrl = "http://localhost:8080/manifest.zipline.json"
        val loader = ZiplineLoader(
            dispatcher,
            ManifestVerifier.NO_SIGNATURE_CHECKS,
            OkHttpClient(),
        )
        return when (val result = loader.loadOnce("game", DefaultFreshnessCheckerNotFresh, manifestUrl)) {
            is LoadResult.Success -> result.zipline
            is LoadResult.Failure -> error(result.exception)
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
