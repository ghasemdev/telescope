package com.parsumash.launcher

import app.cash.redwood.treehouse.TreehouseApp
import app.cash.zipline.Zipline
import app.cash.zipline.ZiplineManifest
import app.cash.zipline.loader.FreshnessChecker
import com.parsumash.treehouse.EmojiSearchPresenter
import com.parsumash.treehouse.HostApi
import com.parsumash.treehouse.emojiSearchSerializersModule
import kotlinx.coroutines.flow.Flow

class EmojiSearchAppSpec(
    override val manifestUrl: Flow<String>,
    private val hostApi: HostApi,
) : TreehouseApp.Spec<EmojiSearchPresenter>() {
    override val name get() = "emoji-search"
    override val serializersModule get() = emojiSearchSerializersModule

    override val freshnessChecker = object : FreshnessChecker {
        override fun isFresh(manifest: ZiplineManifest, freshAtEpochMs: Long) = false
    }

    override suspend fun bindServices(
        treehouseApp: TreehouseApp<EmojiSearchPresenter>,
        zipline: Zipline,
    ) {
        zipline.bind("HostApi", hostApi)
    }

    override fun create(zipline: Zipline): EmojiSearchPresenter {
        return zipline.take("EmojiSearchPresenter")
    }
}
