package com.parsuomash.telescope.di

import android.content.Context
import androidx.startup.Initializer

internal lateinit var applicationContext: Context

internal class ContextInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        applicationContext = context.applicationContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
