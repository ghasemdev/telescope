package com.parsuomash.telescope.data.remote.utils

import io.ktor.client.*
import io.ktor.client.engine.darwin.*

internal actual fun httpClient(
    config: HttpClientConfig<*>.() -> Unit
): HttpClient = HttpClient(Darwin) {
    config(this)
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}
