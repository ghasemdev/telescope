package com.parsuomash.telescope.di

import com.parsuomash.telescope.BuildKonfig
import com.parsuomash.telescope.di.modules.NetworkModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import org.koin.core.KoinApplication
import org.koin.core.logger.KOIN_TAG
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinApplication
import org.koin.dsl.module

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
        if (BuildKonfig.isDevelope) {
            logger(TelescopeLogger())
        }
        modules(NetworkModule, module { single { Foo() } })
    }
}

class Foo {
    fun bar() = "hi"
}

internal class TelescopeLogger(level: Level = Level.INFO) : Logger(level) {
    init {
        Napier.base(DebugAntilog())
    }

    override fun display(level: Level, msg: MESSAGE) {
        when (level) {
            Level.DEBUG -> Napier.d(tag = KOIN_TAG, message = msg)
            Level.INFO -> Napier.i(tag = KOIN_TAG, message = msg)
            Level.WARNING -> Napier.w(tag = KOIN_TAG, message = msg)
            Level.ERROR -> Napier.e(tag = KOIN_TAG, message = msg)
            else -> Napier.e(tag = KOIN_TAG, message = msg)
        }
    }
}
