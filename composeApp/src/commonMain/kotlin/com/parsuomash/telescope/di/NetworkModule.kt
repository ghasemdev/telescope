package com.parsuomash.telescope.di

import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import com.parsuomash.telescope.data.remote.utils.httpClient
import org.koin.dsl.module
import kotlin.time.Duration.Companion.minutes

internal val NetworkModule = module {
  includes(JsonModule)
  single(createdAtStart = true) {
    httpClient {
      install(ContentNegotiation) { json(get()) }
      install(HttpTimeout) {
        requestTimeoutMillis = 2.minutes.inWholeMilliseconds
        connectTimeoutMillis = 2.minutes.inWholeMilliseconds
        socketTimeoutMillis = 2.minutes.inWholeMilliseconds
      }
      install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 5) // status code 500
        exponentialDelay() // 2, 4, 8 ,...
        modifyRequest { request ->
          request.headers.append("x-retry-count", retryCount.toString())
        }
      }
      install(HttpCache) // In Memory
      install(Logging)

      expectSuccess = true
      followRedirects = false

      headers {
        append("contentType", ContentType.Application.Json)
      }
    }
  }
}
