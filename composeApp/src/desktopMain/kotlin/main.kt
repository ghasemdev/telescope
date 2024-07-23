import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.Image
import java.awt.MenuItem
import java.awt.PopupMenu
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

fun main() = application {
    var isVisible by remember { mutableStateOf(true) }

    val trayIcon = remember { TrayIcon(getApplicaionIcon(), "Telescope") }
    val exitMenuItem = remember {
        MenuItem("Exit").apply { addActionListener { exitApplication() } }
    }
    val popupMenu = remember {
        PopupMenu().apply { add(exitMenuItem) }
    }

    DisposableEffect(Unit) {
        if (!trayIcon.isTrayIconAdded()) {
            trayIcon.popupMenu = popupMenu
            SystemTray.getSystemTray().add(trayIcon)
        }
        onDispose {
            SystemTray.getSystemTray().remove(trayIcon)
        }
    }

    DisposableEffect(Unit) {
        val mouseListener = object : MouseAdapter() {
            override fun mouseClicked(event: MouseEvent) {
                if (event.button == MouseEvent.BUTTON1) { // Left click
                    isVisible = true
                }
            }
        }
        trayIcon.addMouseListener(mouseListener)
        trayIcon.addActionListener { isVisible = true }
        onDispose {
            trayIcon.removeMouseListener(mouseListener)
        }
    }

    Window(
        onCloseRequest = { isVisible = false },
        title = "Telescope",
        visible = isVisible
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    trayIcon.displayMessage("Desktop Notification", "message", TrayIcon.MessageType.NONE)
                }
            ) {
                Text("Hello")
            }
        }
    }
}

private fun TrayIcon.isTrayIconAdded(): Boolean = SystemTray.getSystemTray().trayIcons.contains(this)

private fun getApplicaionIcon(): Image = Toolkit.getDefaultToolkit()
    .createImage("src/desktopMain/resources/icon/telescope.png")
