package com.parsuomash.telescope.di.modules

import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal val JsonModule = module {
    single(createdAtStart = true) {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = true
            encodeDefaults = true
        }
    }
}
