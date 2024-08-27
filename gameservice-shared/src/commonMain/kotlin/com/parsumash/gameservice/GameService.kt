package com.parsumash.gameservice

import app.cash.zipline.ZiplineService

interface GameService : ZiplineService {
    fun calculate(): String
}
