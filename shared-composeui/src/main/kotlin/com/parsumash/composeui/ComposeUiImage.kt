package com.parsumash.composeui

import app.cash.redwood.Modifier as RedwoodModifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.compose.AsyncImage
import com.parsumash.schema.widget.Image

internal class ComposeUiImage(
    private val imageLoader: ImageLoader,
) : Image<@Composable () -> Unit> {
    private var url by mutableStateOf("")
    private var onClick by mutableStateOf({})

    override var modifier: RedwoodModifier = RedwoodModifier

    override val value = @Composable {
        val composeModifier = if (modifier.toString().contains("CircleClip")) {
            Modifier.clip(CircleShape)
        } else {
            Modifier
        }

        Box(modifier = composeModifier) {
            AsyncImage(
                model = url,
                imageLoader = imageLoader,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = onClick),
                contentScale = ContentScale.Crop
            )
        }
    }

    override fun url(url: String) {
        this.url = url
    }

    override fun onClick(onClick: (() -> Unit)?) {
        this.onClick = onClick ?: {}
    }
}
