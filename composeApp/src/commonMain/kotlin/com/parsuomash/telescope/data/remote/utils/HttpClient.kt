package com.parsuomash.telescope.data.remote.utils

import io.ktor.client.*

internal expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient
