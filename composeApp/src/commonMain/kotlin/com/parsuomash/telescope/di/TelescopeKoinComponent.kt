package com.parsuomash.telescope.di

import org.koin.core.Koin
import org.koin.core.component.KoinComponent

internal interface TelescopeKoinComponent : KoinComponent {
    override fun getKoin(): Koin = TelescopeKoinContext.app.koin
}
