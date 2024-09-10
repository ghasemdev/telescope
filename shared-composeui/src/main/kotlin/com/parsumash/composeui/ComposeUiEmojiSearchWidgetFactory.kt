package com.parsumash.composeui

import androidx.compose.runtime.Composable
import com.parsumash.schema.modifier.Reuse
import com.parsumash.schema.widget.EmojiSearchWidgetFactory
import com.parsumash.schema.widget.Text
import com.parsumash.schema.widget.TextInput

class ComposeUiEmojiSearchWidgetFactory : EmojiSearchWidgetFactory<@Composable () -> Unit> {
    override fun TextInput(): TextInput<@Composable () -> Unit> = ComposeUiTextInput()
    override fun Text(): Text<@Composable () -> Unit> = ComposeUiText()
    override fun Reuse(value: @Composable () -> Unit, modifier: Reuse) {}
}
