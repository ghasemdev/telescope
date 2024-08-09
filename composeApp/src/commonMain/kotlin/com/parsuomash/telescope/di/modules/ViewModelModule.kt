package com.parsuomash.telescope.di.modules

import com.parsuomash.telescope.presentation.MoviesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val ViewModelModule = module {
    viewModelOf(::MoviesViewModel)
}
