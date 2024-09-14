package com.parsumash.composeui

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import com.parsumash.schema.modifier.CircleClip
import com.parsumash.schema.widget.EmojiSearchWidgetFactory
import com.parsumash.schema.widget.Image
import com.parsumash.schema.widget.Text
import com.parsumash.schema.widget.TextInput

class ComposeUiEmojiSearchWidgetFactory(
    private val imageLoader: ImageLoader,
) : EmojiSearchWidgetFactory<@Composable () -> Unit> {
    override fun TextInput(): TextInput<@Composable () -> Unit> = ComposeUiTextInput()
    override fun Text(): Text<@Composable () -> Unit> = ComposeUiText()
    override fun Image(): Image<@Composable () -> Unit> = ComposeUiImage(imageLoader)
    override fun CircleClip(value: @Composable () -> Unit, modifier: CircleClip) {}
}
