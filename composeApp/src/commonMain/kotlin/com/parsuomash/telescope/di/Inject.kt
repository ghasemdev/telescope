package com.parsuomash.telescope.di

import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatformTools

/**
 * Lazy inject a Koin instance
 * @param qualifier
 * @param scope
 * @param parameters
 *
 * @return Lazy instance of type T
 */
internal inline fun <reified T : Any> inject(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
    noinline parameters: ParametersDefinition? = null,
): T = TelescopeKoinContext.app.koin.inject<T>(
    qualifier = qualifier,
    mode = mode,
    parameters = parameters
).value
