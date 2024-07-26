package com.parsuomash.telescope.compose.tray

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import java.awt.Component
import java.awt.ComponentOrientation
import java.awt.GraphicsConfiguration
import java.awt.GraphicsEnvironment
import java.util.*

internal val GlobalDensity
    get() = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .defaultScreenDevice
        .defaultConfiguration
        .density

internal val Component.density: Density get() = graphicsConfiguration.density

internal val Component.sizeInPx: Size
    get() {
        val scale = density.density
        return Size(
            width = width * scale,
            height = height * scale
        )
    }

private val GraphicsConfiguration.density: Density
    get() = Density(
        defaultTransform.scaleX.toFloat(),
        fontScale = 1f
    )

internal val GlobalLayoutDirection get() = Locale.getDefault().layoutDirection

internal val Locale.layoutDirection: LayoutDirection
    get() = ComponentOrientation.getOrientation(this).layoutDirection

internal val ComponentOrientation.layoutDirection: LayoutDirection
    get() = when {
        isLeftToRight -> LayoutDirection.Ltr
        isHorizontal -> LayoutDirection.Rtl
        else -> LayoutDirection.Ltr
    }

internal val LayoutDirection.componentOrientation: ComponentOrientation
    get() = when (this) {
        LayoutDirection.Ltr -> ComponentOrientation.LEFT_TO_RIGHT
        LayoutDirection.Rtl -> ComponentOrientation.RIGHT_TO_LEFT
    }

/**
 * Compute the [LayoutDirection] the given AWT/Swing component should have, based on its own,
 * non-Compose attributes.
 */
internal fun layoutDirectionFor(component: Component): LayoutDirection {
    val orientation = component.componentOrientation
    return if (orientation != ComponentOrientation.UNKNOWN) {
        orientation.layoutDirection
    } else {
        // To preserve backwards compatibility we fall back to the locale
        return component.locale.layoutDirection
    }
}
