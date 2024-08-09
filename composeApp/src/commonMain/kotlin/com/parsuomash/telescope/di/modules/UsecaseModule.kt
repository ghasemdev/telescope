package com.parsuomash.telescope.di.modules

import com.parsuomash.telescope.domain.usecase.GetMoviesUsecase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val UsecaseModule = module {
    factoryOf(::GetMoviesUsecase)
}
