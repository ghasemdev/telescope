package com.parsuomash.telescope.di.modules

import com.parsuomash.telescope.data.remote.datasource.MovieRemoteDataSource
import com.parsuomash.telescope.data.remote.datasource.MovieRemoteDataSourceImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val DataSourceModule = module {
    factoryOf(::MovieRemoteDataSourceImpl) bind MovieRemoteDataSource::class
}
