package com.parsumash.presenter

import androidx.compose.runtime.Composable
import app.cash.redwood.treehouse.TreehouseUi

class EmojiSearchTreehouseUi(
    private val httpClient: HttpClient,
    private val navigator: Navigator,
) : TreehouseUi {
    @Composable
    override fun Show() {
        EmojiSearch(httpClient, navigator)
    }
}
