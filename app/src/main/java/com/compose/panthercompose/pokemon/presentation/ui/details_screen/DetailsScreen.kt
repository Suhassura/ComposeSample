package com.compose.panthercompose.pokemon.presentation.ui.details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.compose.panthercompose.pokemon.presentation.theme.SystemUiController

@Composable
fun DetailsScreen(
    dominantColor: Color,
    pokemonName: String,
    viewModel: DetailsViewModel = hiltNavGraphViewModel()
) {
    viewModel.pokemonDetails(pokemonName)
    val defaultDominantColor = MaterialTheme.colors.surface

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                top = 40.dp,
                bottom = 16.dp,
                end = 16.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            dominantColor,
                            defaultDominantColor
                        )
                    )
                )
        ) {

        }
    }

}