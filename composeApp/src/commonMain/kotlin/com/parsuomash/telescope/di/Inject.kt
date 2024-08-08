package com.parsuomash.telescope.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
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

/**
 * Resolve Koin dependency
 *
 * @param qualifier
 * @param scope - Koin's root default
 * @param parameters - injected parameters
 *
 * @author Arnaud Giuliani
 */
@OptIn(KoinInternalApi::class)
@Composable
internal inline fun <reified T> koinInject(
    qualifier: Qualifier? = null,
    scope: Scope = TelescopeKoinContext.app.koin.scopeRegistry.rootScope,
    noinline parameters: ParametersDefinition? = null,
): T {
    // This will always refer to the latest parameters
    val currentParameters by rememberUpdatedState(parameters)

    return remember(qualifier, scope) {
        scope.get(qualifier) {
            currentParameters?.invoke() ?: emptyParametersHolder()
        }
    }
}
