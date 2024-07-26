package com.parsuomash.telescope

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState
import com.parsuomash.telescope.compose.notifier.ProvideTrayState
import com.parsuomash.telescope.compose.tray.Tray
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import telescope.composeapp.generated.resources.Res
import telescope.composeapp.generated.resources.app_name
import telescope.composeapp.generated.resources.telescope

fun main() = application {
    var isTelscopeWindowVisible by rememberSaveable { mutableStateOf(true) }
    var isTelscopeTrayVisible by rememberSaveable { mutableStateOf(true) }
    val trayState = rememberTrayState()

    if (isTelscopeTrayVisible) {
        Tray(
            state = trayState,
            icon = painterResource(Res.drawable.telescope),
            tooltip = stringResource(Res.string.app_name),
            onAction = {
                isTelscopeWindowVisible = true
            },
            onClick = {
                isTelscopeWindowVisible = true
            }
        ) {
            Item(
                text = "Open Telescope",
                shortcut = null,
                onClick = {
                    isTelscopeWindowVisible = true
                }
            )
            Item(
                text = "Quit Telescope",
                shortcut = null,
                onClick = {
                    isTelscopeWindowVisible = false
                    isTelscopeTrayVisible = false
                }
            )
        }
    }

    Window(
        onCloseRequest = { isTelscopeWindowVisible = false },
        title = stringResource(Res.string.app_name),
        icon = painterResource(Res.drawable.telescope),
        visible = isTelscopeWindowVisible
    ) {
        ProvideTrayState(trayState) {
            App()
        }
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Contracts"
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Hello")
        }
    }
}
