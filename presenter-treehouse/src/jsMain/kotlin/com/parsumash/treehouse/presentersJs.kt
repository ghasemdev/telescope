package com.parsumash.treehouse

import app.cash.zipline.Zipline

private val zipline by lazy { Zipline.get(emojiSearchSerializersModule) }

@OptIn(ExperimentalJsExport::class)
@JsExport
fun preparePresenters() {
    val hostApi = zipline.take<HostApi>(
        name = "HostApi",
    )

    zipline.bind<EmojiSearchPresenter>(
        name = "EmojiSearchPresenter",
        instance = RealEmojiSearchPresenter(hostApi, zipline.json),
    )
}
