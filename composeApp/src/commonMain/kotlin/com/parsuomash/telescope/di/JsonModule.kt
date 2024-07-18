package com.parsuomash.telescope.di

import org.koin.dsl.module
import kotlinx.serialization.json.Json

internal val JsonModule = module {
  single(createdAtStart = true) {
    Json {
      prettyPrint = true
      isLenient = true
    }
  }
}
