package com.parsuomash.telescope

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.parsuomash.telescope.data.remote.model.BoxWrapper
import com.parsuomash.telescope.data.remote.model.FColor
import com.parsuomash.telescope.data.remote.model.FModifier
import com.parsuomash.telescope.di.TelescopeKoinContext
import com.parsuomash.telescope.di.scope.ActivityScope
import com.parsuomash.telescope.notifier.NotificationConfiguration
import com.parsuomash.telescope.notifier.NotifierManager
import com.parsuomash.telescope.notifier.ProvideNotificationConfiguration
import com.parsuomash.telescope.notifier.extensions.onCreateOrOnNewIntent
import com.parsuomash.telescope.notifier.permission.notificationPermissionRequester
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext

class MainActivity : ActivityScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TelescopeKoinContext.start {
            androidContext(applicationContext)
        }

        val notificationPermission by notificationPermissionRequester()
        notificationPermission.askNotificationPermission()

        NotifierManager.onCreateOrOnNewIntent(intent)

        val ui = readJsonFile()

        setContent {
            val notificationConfiguration = remember {
                NotificationConfiguration.Android(
                    notificationIconResId = R.drawable.ic_telescope,
                    notificationIconColorResId = R.color.notification_icon
                )
            }

            ProvideNotificationConfiguration(notificationConfiguration) {
                DynamicBox(ui)
            }
        }
    }

    @Composable
    fun DynamicBox(boxWrapper: BoxWrapper) {
        val box = boxWrapper.box
        val modifier = Modifier.composeModifier(box.modifiers)

        Box(modifier = modifier) {
            if (box.content != null) {
                DynamicBox(BoxWrapper(box.content))
            }
        }
    }

    @Composable
    private fun Modifier.composeModifier(modifiers: List<FModifier>): Modifier {
        var currentModifier = this
        modifiers.forEach { modifier ->
            modifier.size?.let {
                currentModifier = currentModifier.size(it.parseSize())
            }
            modifier.background?.let {
                currentModifier = currentModifier.background(it.parseColor())
            }
        }
        return currentModifier
    }

    @Composable
    private fun String.parseSize(): Dp {
        val (size, unit) = split(".")
        return when (unit) {
            "dp" -> size.toFloatOrNull()?.dp ?: error("size value must be an integer or float.")
            "px" -> size.toFloatOrNull()?.pxToDp() ?: error("size value must be an integer or float.")
            else -> error("Only dp and px support (10.dp, 15.px).")
        }
    }

    @Composable
    fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

    private fun String.parseColor(): Color {
        return if (startsWith("#")) {
            Color(android.graphics.Color.parseColor(this))
        } else {
            val color = FColor.parseFrom(this) ?: error("Colors must be in Hex format or Constants")
            color.toComposeColor()
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

    private fun readJsonFile(): BoxWrapper {
        val jsonString = resources.openRawResource(R.raw.ui)
            .bufferedReader()
            .readText()
        return Json { ignoreUnknownKeys = true }.decodeFromString<BoxWrapper>(jsonString)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
