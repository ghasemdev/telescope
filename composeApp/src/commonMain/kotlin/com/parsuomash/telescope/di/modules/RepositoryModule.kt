package com.parsuomash.telescope.di.modules

import com.parsuomash.telescope.data.repository.MovieRepositoryImpl
import com.parsuomash.telescope.domain.repository.MovieRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val RepositoryModule = module {
    factoryOf(::MovieRepositoryImpl) bind MovieRepository::class
}
