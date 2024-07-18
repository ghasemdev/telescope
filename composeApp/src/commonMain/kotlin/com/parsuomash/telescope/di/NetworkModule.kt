package com.parsuomash.telescope.di

import com.parsuomash.telescope.data.remote.utils.httpClient
import org.koin.dsl.module

internal val NetworkModule = module {
  single(createdAtStart = true) { httpClient() }
}
