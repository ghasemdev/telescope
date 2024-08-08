package com.parsuomash.telescope.di

import com.parsuomash.telescope.di.modules.NetworkModule
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinApplication

internal object TelescopeKoinContext {
    private val lock = SynchronizedObject()
    private var koinApplication: KoinApplication? = null

    val app: KoinApplication
        get() = koinApplication ?: error("koin Application for TelescopeKoinContext has not been started!!")

    fun start(appDeclaration: KoinAppDeclaration? = null) {
        synchronized(lock) {
            if (koinApplication == null) {
                koinApplication = buildKoinApplication(appDeclaration)
            }
        }
    }

    fun stop() {
        synchronized(lock) {
            koinApplication?.close()
            koinApplication = null
        }
    }

    fun loadKoinModules(vararg modules: Module) {
        synchronized(lock) {
            app.koin.loadModules(modules.toList())
        }
    }

    fun unloadKoinModules(vararg modules: Module) {
        synchronized(lock) {
            app.koin.unloadModules(modules.toList())
        }
    }

    private fun buildKoinApplication(
        appDeclaration: KoinAppDeclaration?
    ): KoinApplication = koinApplication {
        appDeclaration?.invoke(this)
        modules(NetworkModule)
    }
}
