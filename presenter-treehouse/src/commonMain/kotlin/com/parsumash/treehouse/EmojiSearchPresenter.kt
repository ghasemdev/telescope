package com.parsumash.treehouse

import app.cash.redwood.treehouse.AppService
import app.cash.redwood.treehouse.ZiplineTreehouseUi
import app.cash.zipline.ZiplineService
import kotlin.native.ObjCName

@ObjCName("EmojiSearchPresenter", exact = true)
interface EmojiSearchPresenter : AppService, ZiplineService {
    fun launch(): ZiplineTreehouseUi
}
