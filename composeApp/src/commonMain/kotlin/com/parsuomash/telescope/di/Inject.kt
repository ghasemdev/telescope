package com.parsuomash.telescope.di

import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools

internal inline fun <reified T : Any> inject(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> = TelescopeKoinContext.get().koin.inject<T>(
    qualifier = qualifier,
    mode = mode,
    parameters = parameters
)
