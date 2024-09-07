package com.parsuomash.telescope

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import app.cash.zipline.ZiplineManifest
import app.cash.zipline.loader.FreshnessChecker
import app.cash.zipline.loader.LoadResult
import app.cash.zipline.loader.ManifestVerifier
import app.cash.zipline.loader.ZiplineCache
import app.cash.zipline.loader.ZiplineLoader
import com.parsumash.gameservice.GameService
import com.parsuomash.telescope.di.TelescopeKoinContext
import com.parsuomash.telescope.di.scope.ActivityScope
import com.parsuomash.telescope.notifier.NotificationConfiguration
import com.parsuomash.telescope.notifier.NotifierManager
import com.parsuomash.telescope.notifier.ProvideNotificationConfiguration
import com.parsuomash.telescope.notifier.extensions.onCreateOrOnNewIntent
import com.parsuomash.telescope.notifier.permission.notificationPermissionRequester
import java.io.File
import java.util.concurrent.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import org.koin.android.ext.koin.androidContext

class MainActivity : ActivityScope() {
    private var game: String by mutableStateOf("yufuy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            launchZipline()
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = game)
                }
            }
        }
    }

    fun launchZipline() {
        val executorService = Executors.newFixedThreadPool(1) {
            Thread(it, "Zipline")
        }
        val dispatcher = executorService.asCoroutineDispatcher()

        lifecycleScope.launch(dispatcher + SupervisorJob()) {
            val manifestUrl = "http://172.30.250.217:8080/manifest.zipline.json"
            val cache = ZiplineCache(
                context = this@MainActivity,
                fileSystem = FileSystem.SYSTEM,
                directory = File(cacheDir, "zipline_cache").toOkioPath(),
                maxSizeInBytes = 10 * 1024 * 1024
            )
            val loader = ZiplineLoader(
                dispatcher,
                ManifestVerifier.NO_SIGNATURE_CHECKS,
                OkHttpClient()
            ).withCache(cache, dispatcher)

            val loadResultFlow = loader.load(
                applicationName = "game",
                freshnessChecker = object : FreshnessChecker {
                    override fun isFresh(
                        manifest: ZiplineManifest,
                        freshAtEpochMs: Long
                    ): Boolean = (System.currentTimeMillis() - freshAtEpochMs) < 5.minutes.inWholeMilliseconds
                },
                manifestUrlFlow = repeatFlow(manifestUrl, 1.minutes)
            )

            var previousJob: Job? = null

            loadResultFlow.collect { result ->
                previousJob?.cancel()

                if (result is LoadResult.Success) {
                    val zipline = result.zipline
                    val presenter = zipline.take<GameService>("gameService")

                    val job = launch {
                        game = presenter.calculate()
                    }

                    job.invokeOnCompletion {
                        presenter.close()
                        zipline.close()
                    }

                    previousJob = job
                }
            }
        }
    }

    /** Poll for code updates by emitting the manifest on an interval. */
    private fun <T> repeatFlow(content: T, delay: Duration): Flow<T> {
        return flow {
            while (true) {
                emit(content)
                delay(delay)
            }
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
