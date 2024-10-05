package com.parsuomash.telescope.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.parsuomash.navigation.core.tryIgnore
import com.parsuomash.navigation.core.tryOrEmpty
import com.parsuomash.navigation.safeAddRoute
import com.parsuomash.telescope.theme.LocalFontFamily
import kotlinx.coroutines.launch

class RegisterScreen : Screen {
    private fun getNationalCode(): String = tryOrEmpty {
        js("JSInterface.getNationalCode();").unsafeCast<String>()
    }

    private fun finishActivity() {
        tryIgnore {
            js("JSInterface.finishActivity();") as Unit
        }
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val byekanFamily = LocalFontFamily.current

        val coroutineScope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(Hidden)

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color(0xFF152132)
        ) {
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeightIn(400.dp)
                            .background(color = Color(0xFF19273B), shape = RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "انتخاب تاریخ",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = byekanFamily
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .requiredHeightIn(min = 54.dp),
                            onClick = {
                                coroutineScope.launch {
                                    sheetState.hide()
                                    safeAddRoute(route = "DashboardScreen")
                                    navigator.push(DashboardScreen())
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0XFF40BED0),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "تایید",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = byekanFamily
                            )
                        }
                    }
                },
                sheetState = sheetState,
                sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                sheetContentColor = Color(0xFF19273B)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
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
                            onClick = {
                                finishActivity()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = Color.White,
                                contentDescription = null
                            )
                        }
                    }
                    Screen(byekanFamily = byekanFamily, sheetState = sheetState)
                }
            }
        }
    }

    @Composable
    private fun Screen(
        byekanFamily: FontFamily,
        sheetState: ModalBottomSheetState
    ) {
        var phone by remember { mutableStateOf("") }
        var natonal by remember { mutableStateOf(getNationalCode()) }
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxWidth(
                    if (currentWindowAdaptiveInfo()
                            .windowSizeClass
                            .windowWidthSizeClass == WindowWidthSizeClass.COMPACT
                    ) 1f else .5f
                )
                .fillMaxHeight()
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextField(
                        value = phone,
                        onValueChange = {
                            if (it.length != 12) {
                                phone = it
                            }
                        },
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
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        ),
                        maxLines = 1,
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
                    TextField(
                        value = natonal,
                        onValueChange = {
                            if (it.length != 12) {
                                natonal = it
                            }
                        },
                        modifier = Modifier.fillMaxWidth().requiredHeightIn(54.dp),
                        placeholder = {
                            Text(
                                text = "کد ملی",
                                color = Color(0xFFCCCACF),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = byekanFamily
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        ),
                        maxLines = 1,
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
                    onClick = {
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
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
