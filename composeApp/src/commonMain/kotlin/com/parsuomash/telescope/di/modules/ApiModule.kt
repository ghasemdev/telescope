package com.parsuomash.telescope.di.modules

import com.parsuomash.telescope.data.remote.api.MovieApi
import com.parsuomash.telescope.data.remote.api.MovieApiImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val ApiModule = module {
    factoryOf(::MovieApiImpl) bind MovieApi::class
}
