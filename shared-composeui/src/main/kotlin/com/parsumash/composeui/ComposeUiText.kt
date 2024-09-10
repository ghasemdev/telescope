package com.parsumash.composeui
//
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import app.cash.redwood.Modifier
//
//internal class ComposeUiText : Text<@Composable () -> Unit> {
//    private var text by mutableStateOf("")
//
//    override var modifier: Modifier = Modifier
//
//    override val value = @Composable {
//        Text(
//            text = text,
//            color = MaterialTheme.colors.onBackground,
//        )
//    }
//
//    override fun text(text: String) {
//        this.text = text
//    }
//}
