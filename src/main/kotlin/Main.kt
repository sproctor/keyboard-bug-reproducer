import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.event.KeyEvent

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("No key pressed") }
    val focusRequester = remember { FocusRequester() }

    MaterialTheme {
        Column {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.focusRequester(focusRequester)
                    .onKeyEvent { event ->
                        message = when (event.key) {
                            Key.NumPadDot -> "expected behavior"
                            Key(KeyEvent.VK_DECIMAL, KeyEvent.KEY_LOCATION_NUMPAD) -> "matched VK_DECIMAL"
                            else -> "wrong key pressed"
                        }
                        false
                    }
            )
            Text("press a key")
            Text(message)
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        focusRequester.captureFocus()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
