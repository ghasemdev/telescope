package com.parsumash.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import app.cash.redwood.Modifier
import app.cash.redwood.compose.LocalUiConfiguration
import app.cash.redwood.layout.api.Constraint
import app.cash.redwood.layout.api.CrossAxisAlignment
import app.cash.redwood.layout.api.MainAxisAlignment
import app.cash.redwood.layout.compose.Column
import app.cash.redwood.layout.compose.Row
import app.cash.redwood.lazylayout.compose.ExperimentalRedwoodLazyLayoutApi
import app.cash.redwood.lazylayout.compose.LazyColumn
import app.cash.redwood.lazylayout.compose.items
import app.cash.redwood.lazylayout.compose.rememberLazyListState
import app.cash.redwood.ui.Margin
import app.cash.redwood.ui.dp
import com.parsumash.schema.compose.Image
import com.parsumash.schema.compose.Text
import com.parsumash.schema.compose.TextInput
import com.parsumash.schema.compose.reuse
import com.parsumash.values.TextFieldState
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

data class EmojiImage(
    val label: String,
    val url: String,
)

// TODO Switch to https://github.com/cashapp/zipline/issues/490 once available.
@Suppress("FUN_INTERFACE_WITH_SUSPEND_FUNCTION") // https://youtrack.jetbrains.com/issue/KTIJ-7642
fun interface HttpClient {
    suspend fun call(url: String, headers: Map<String, String>): String
}

interface Navigator {
    /** Open a URL in the app that owns it. For example, a browser. */
    fun openUrl(url: String)
}

@Composable
@OptIn(ExperimentalRedwoodLazyLayoutApi::class)
fun EmojiSearch(
    httpClient: HttpClient,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    safeAreaInsets: Margin = LocalUiConfiguration.current.safeAreaInsets,
) {
    val scope = rememberCoroutineScope()
    val allEmojis = remember { mutableStateListOf<EmojiImage>() }

    // Simple counter that allows us to trigger refreshes by simple incrementing the value
    var refreshSignal by remember { mutableIntStateOf(0) }
    var refreshing by remember { mutableStateOf(false) }

    val searchTermSaver = object : Saver<TextFieldState, String> {
        override fun restore(value: String) = TextFieldState(value)
        override fun SaverScope.save(value: TextFieldState) = value.text
    }

    var searchTerm by rememberSaveable(stateSaver = searchTermSaver) { mutableStateOf(TextFieldState("")) }

    val lazyListState = rememberLazyListState()

    LaunchedEffect(searchTerm) {
        lazyListState.programmaticScroll(0, animated = true)
    }

    LaunchedEffect(refreshSignal) {
        try {
            refreshing = true
            val emojisJson = httpClient.call(
                url = "https://api.github.com/emojis",
                headers = mapOf("Accept" to "application/vnd.github.v3+json"),
            )
            val labelToUrl = Json.decodeFromString<Map<String, String>>(emojisJson)

            allEmojis.clear()
            var index = 0
            allEmojis.addAll(labelToUrl.map { (key, value) -> EmojiImage("${index++}. $key", value) })
        } finally {
            refreshing = false
        }
    }

    val filteredEmojis by remember {
        derivedStateOf {
            val searchTerms = searchTerm.text.split(" ")
            allEmojis.filter { image ->
                searchTerms.all { image.label.contains(it, ignoreCase = true) }
            }
        }
    }

    Column(
        width = Constraint.Fill,
        height = Constraint.Fill,
        horizontalAlignment = CrossAxisAlignment.Stretch,
        margin = safeAreaInsets,
        modifier = modifier,
    ) {
        Text(text = "Hi Saeed KHobi")
        TextInput(
            state = TextFieldState(searchTerm.text),
            hint = "Search",
            onChange = { textFieldState ->
                // Make it easy to trigger a crash to manually test exception handling!
                when (textFieldState.text) {
                    "crash" -> throw RuntimeException("boom!")

                    "async" -> {
                        scope.launch {
                            throw RuntimeException("boom!")
                        }
                    }
                }

                searchTerm = textFieldState
            },
        )
        LazyColumn(
            refreshing = refreshing,
            onRefresh = { refreshSignal++ },
            state = lazyListState,
            width = Constraint.Fill,
            modifier = Modifier.flex(1.0),
            placeholder = {
                Item(
                    emojiImage = loadingEmojiImage,
                    onClick = {},
                )
            },
        ) {
            items(filteredEmojis) { image ->
                Item(
                    modifier = Modifier.reuse(),
                    emojiImage = image,
                    onClick = {
                        navigator.openUrl(image.url)
                    },
                )
            }
        }
    }
}

@Composable
fun Item(
    emojiImage: EmojiImage,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        width = Constraint.Fill,
        height = Constraint.Wrap,
        verticalAlignment = CrossAxisAlignment.Center,
        horizontalAlignment = MainAxisAlignment.Start,
    ) {
        Image(
            url = emojiImage.url,
            modifier = Modifier
                .margin(Margin(8.dp))
                .size(24.dp, 24.dp),
            onClick = onClick,
        )
        Text(text = emojiImage.label)
    }
}

val loadingEmojiImage = EmojiImage(
    label = "loading…",
    url = "https://github.githubassets.com/images/icons/emoji/unicode/231a.png?v8",
)
