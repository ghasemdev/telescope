package com.parsumash.gameservice

import app.cash.zipline.Zipline

@OptIn(ExperimentalJsExport::class)
@JsExport
fun launchZipline() {
    val zipline = Zipline.get()
    zipline.bind<GameService>("triviaService", GameServiceImpl())
}
