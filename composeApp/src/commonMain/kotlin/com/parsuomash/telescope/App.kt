package com.parsuomash.telescope

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.parsuomash.telescope.di.Foo
import com.parsuomash.telescope.di.koinInject
import com.parsuomash.telescope.notifier.rememberNotifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val notifier = rememberNotifier()

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        notifier.notify(
                            title = "Desktop Notification",
                            message = "message"
                        )
                    }
                ) {
                    Text("Show Notif ${koinInject<Foo>().bar()}")
                }
            }
        }
    }
}
