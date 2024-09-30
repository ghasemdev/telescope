package com.parsuomash.telescope

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.CanvasBasedWindow
import com.parsuomash.telescope.di.TelescopeKoinContext
import com.parsuomash.telescope.notifier.NotificationConfiguration
import com.parsuomash.telescope.notifier.ProvideNotificationConfiguration
import org.jetbrains.compose.resources.Font
import org.jetbrains.skiko.wasm.onWasmReady
import telescope.composeapp.generated.resources.Res
import telescope.composeapp.generated.resources.byekan
import telescope.composeapp.generated.resources.byekan_bold

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    TelescopeKoinContext.start()

    onWasmReady {
        CanvasBasedWindow(canvasElementId = "appTarget") {
            val byekanFamily = FontFamily(
                Font(resource = Res.font.byekan, FontWeight.Normal),
                Font(resource = Res.font.byekan_bold, FontWeight.Bold)
            )

            var phone by remember { mutableStateOf("") }

            val notificationConfiguration = remember {
                NotificationConfiguration.Web(notificationIconPath = "telescope.png")
            }

            DisposableEffect(Unit) {
                onDispose {
                    TelescopeKoinContext.stop()
                }
            }

            ProvideNotificationConfiguration(notificationConfiguration) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = Color(0xFF152132)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "امضاء دیجیتال",
                                    modifier = Modifier.align(Alignment.Center),
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = byekanFamily
                                )
                                IconButton(
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    onClick = {}
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        tint = Color.White,
                                        contentDescription = null
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(48.dp)
                                        .border(width = 1.dp, color = Color(0xFF21354F), shape = CircleShape)
                                        .size(128.dp)
                                        .background(color = Color(0xFF19273B), shape = CircleShape)
                                        .padding(30.dp),
                                    imageVector = Icons.Default.Edit,
                                    tint = Color.White,
                                    contentDescription = null
                                )
                                Text(
                                    modifier = Modifier.padding(bottom = 16.dp),
                                    text = "کاربر گرامی، جهت استفاده از خدمات امضای دیجیتال لطفا اطلاعات زیر را تکمیل کنید.",
                                    color = Color(0xFFCCCACF),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = byekanFamily
                                )
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        TextField(
                                            value = phone,
                                            onValueChange = { phone = it },
                                            modifier = Modifier.fillMaxWidth().requiredHeightIn(54.dp),
                                            placeholder = {
                                                Text(
                                                    text = "شماره موبایل",
                                                    color = Color(0xFFCCCACF),
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Normal,
                                                    fontFamily = byekanFamily
                                                )
                                            },
                                            singleLine = true,
                                            shape = RoundedCornerShape(16.dp),
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color.White,
                                                backgroundColor = Color(0xFF19273B),
                                                cursorColor = Color(0xFF50CD89),
                                                focusedLabelColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent,
                                                errorIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                disabledIndicatorColor = Color.Transparent
                                            ),
                                        )
                                    }
                                    Button(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 16.dp)
                                            .requiredHeightIn(min = 54.dp),
                                        onClick = {},
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(0XFF40BED0),
                                            contentColor = Color.White
                                        ),
                                        shape = RoundedCornerShape(16.dp)
                                    ) {
                                        Text(
                                            text = "تایید و ادامه",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = byekanFamily
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
