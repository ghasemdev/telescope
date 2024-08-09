package com.parsuomash.telescope.data.remote.utils

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

internal actual fun httpClient(
    config: HttpClientConfig<*>.() -> Unit
): HttpClient = HttpClient(OkHttp) {
    config(this)
}
