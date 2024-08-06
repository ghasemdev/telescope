package com.parsuomash.telescope.di

import com.parsuomash.telescope.di.modules.NetworkModule
import kotlin.jvm.JvmStatic
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinApplication

internal object TelescopeKoinContext {
    private val lock = SynchronizedObject()
    private var app: KoinApplication? = null

    @JvmStatic
    fun start(appDeclaration: KoinAppDeclaration? = null) = synchronized(lock) {
        if (app == null) {
            app = buildKoinApplication(appDeclaration)
        }
    }

    @JvmStatic
    fun stop() = synchronized(lock) {
        app?.close()
        app = null
    }

    @JvmStatic
    fun get(): KoinApplication = app ?: error("koin Application for TelescopeKoinContext has not been started!!")

    @JvmStatic
    fun loadKoinModules(vararg modules: Module) {
        get().koin.loadModules(modules.toList())
    }

    @JvmStatic
    fun unloadKoinModules(vararg modules: Module) {
        get().koin.unloadModules(modules.toList())
    }

    private fun buildKoinApplication(appDeclaration: KoinAppDeclaration?): KoinApplication = koinApplication {
        appDeclaration?.invoke(this)
        modules(NetworkModule)
    }
}
