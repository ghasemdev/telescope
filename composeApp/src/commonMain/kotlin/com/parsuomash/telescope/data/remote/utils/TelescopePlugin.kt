package com.parsuomash.telescope.data.remote.utils

import io.ktor.client.plugins.api.*

class TelescopePluginConfig {
    var headerName: String = "X-Custom-Header"
    var headerValue: String = "Default value"
}

val TelescopePlugin = createClientPlugin("TelescopePlugin", ::TelescopePluginConfig) {
    val headerName = pluginConfig.headerName
    val headerValue = pluginConfig.headerValue

    onRequest { request, _ ->
//        request.headers.append(headerName, headerValue)

        println("Request headers:")
        request.headers.entries().forEach { entry ->
            printHeader(entry)
        }
    }
    onResponse { response ->
        println("Response headers:")
        response.headers.entries().forEach { entry ->
            printHeader(entry)
        }
    }
}

private fun printHeader(entry: Map.Entry<String, List<String>>) {
    var headerString = entry.key + ": "
    entry.value.forEach { headerValue ->
        headerString += "${headerValue};"
    }
    println("-> $headerString")
}
