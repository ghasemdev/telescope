package com.parsuomash.telescope.data.remote.utils

import io.ktor.client.*
import io.ktor.client.engine.js.*

internal actual fun httpClient(
    config: HttpClientConfig<*>.() -> Unit
): HttpClient = HttpClient(Js) {
    config(this)
}
