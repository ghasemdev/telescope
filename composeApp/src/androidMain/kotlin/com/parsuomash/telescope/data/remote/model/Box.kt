package com.parsuomash.telescope.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class BoxWrapper(
    val box: Box
)

@Serializable
data class Box(
    val modifiers: List<FModifier> = listOf(),
    val content: Box? = null
)

@Serializable
data class FModifier(
    val size: String? = null,
    val background: String? = null
)
