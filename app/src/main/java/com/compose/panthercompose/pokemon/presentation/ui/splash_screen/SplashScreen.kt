package com.compose.panthercompose.pokemon.presentation.ui.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.popUpTo
import com.compose.panthercompose.R
import com.compose.panthercompose.pokemon.presentation.theme.SystemUiController

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltNavGraphViewModel()
) {

   // systemUiController.setStatusBarColor(MaterialTheme.colors.onPrimary)
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
        Image(
            painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
            contentDescription = "Splash Image",
            modifier = Modifier
                .wrapContentSize()
                .align(CenterHorizontally),
            alignment = Alignment.Center,
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