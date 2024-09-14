package com.parsumash.schema

import app.cash.redwood.layout.RedwoodLayout
import app.cash.redwood.lazylayout.RedwoodLazyLayout
import app.cash.redwood.schema.Modifier
import app.cash.redwood.schema.Property
import app.cash.redwood.schema.Schema
import app.cash.redwood.schema.Schema.Dependency
import app.cash.redwood.schema.Widget
import com.parsumash.values.TextFieldState

@Schema(
    members = [
        TextInput::class,
        Text::class,
        Image::class,
        CircleClip::class,
    ],
    dependencies = [
        Dependency(1, RedwoodLayout::class),
        Dependency(2, RedwoodLazyLayout::class),
    ],
)
interface EmojiSearch

@Widget(1)
data class TextInput(
    @Property(1)
    val state: TextFieldState = TextFieldState(),
    @Property(2)
    val hint: String = "",
    @Property(3)
    val onChange: ((TextFieldState) -> Unit)? = null,
)

@Widget(2)
data class Text(
    @Property(1) val text: String,
)

@Widget(3)
data class Image(
    @Property(1) val url: String,
    @Property(2) val onClick: (() -> Unit)? = null,
)

@Modifier(1)
data object CircleClip
