import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.parsuomash.telescope.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Telescope",
    ) {
        App()
    }
}