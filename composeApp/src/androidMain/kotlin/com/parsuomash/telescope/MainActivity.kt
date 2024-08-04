package com.parsuomash.telescope

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.parsuomash.telescope.compose.notifier.NotificationConfiguration
import com.parsuomash.telescope.compose.notifier.NotifierManager
import com.parsuomash.telescope.compose.notifier.ProvideNotificationConfiguration
import com.parsuomash.telescope.compose.notifier.permission.notificationPermissionRequester
import com.parsuomash.telescope.core.extensions.onCreateOrOnNewIntent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        NotifierManager.onCreateOrOnNewIntent(intent)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
