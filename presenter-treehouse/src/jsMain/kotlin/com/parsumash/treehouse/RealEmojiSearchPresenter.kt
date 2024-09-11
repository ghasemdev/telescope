package com.parsumash.treehouse

import app.cash.redwood.treehouse.StandardAppLifecycle
import app.cash.redwood.treehouse.ZiplineTreehouseUi
import app.cash.redwood.treehouse.asZiplineTreehouseUi
import com.parsumash.presenter.EmojiSearchTreehouseUi
import com.parsumash.presenter.Navigator
import com.parsumash.schema.protocol.guest.EmojiSearchProtocolWidgetSystemFactory
import kotlinx.serialization.json.Json

class RealEmojiSearchPresenter(
    private val hostApi: HostApi,
    json: Json,
) : EmojiSearchPresenter {
    override val appLifecycle = StandardAppLifecycle(
        protocolWidgetSystemFactory = EmojiSearchProtocolWidgetSystemFactory,
        json = json,
        widgetVersion = 0U,
    )

    override fun launch(): ZiplineTreehouseUi {
        val navigator = object : Navigator {
            override fun openUrl(url: String) {
                hostApi.openUrl(url)
            }
        }
        val treehouseUi = EmojiSearchTreehouseUi(hostApi::httpCall, navigator)
        return treehouseUi.asZiplineTreehouseUi(
            appLifecycle = appLifecycle,
        )
    }
}
