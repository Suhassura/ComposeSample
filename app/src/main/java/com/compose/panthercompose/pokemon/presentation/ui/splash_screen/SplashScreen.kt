package com.compose.panthercompose.pokemon.presentation.ui.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.compose.panthercompose.pokemon.presentation.theme.fontFamily

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltNavGraphViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Red,
                        Color.Yellow
                    ), tileMode = TileMode.Mirror
                )
            ),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontSize = 54.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("P")
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = 48.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                ) {
                    append("AN")
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = 52.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Red
                    )
                ) {
                    append("T")
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = 32.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Green
                    )
                ) {
                    append("HER")
                }
            },
            modifier = Modifier
                .wrapContentSize()
                .align(CenterHorizontally)
        )
    }

    if (viewModel.isCompleted.value) {
        navController.navigate("home_screen") {
            popUpTo("splash_screen") {
                inclusive = true
            }
        }
        viewModel.isCompleted.value = false
    }
}