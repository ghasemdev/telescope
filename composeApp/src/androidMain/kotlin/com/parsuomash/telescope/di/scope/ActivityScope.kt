package com.parsuomash.telescope.di.scope

import androidx.activity.ComponentActivity
import com.parsuomash.telescope.di.TelescopeKoinComponent
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope

abstract class ActivityScope : ComponentActivity(), TelescopeKoinComponent, AndroidScopeComponent {
    override val scope: Scope by activityScope()
}
