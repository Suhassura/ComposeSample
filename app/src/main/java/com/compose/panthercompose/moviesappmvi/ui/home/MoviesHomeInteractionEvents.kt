package com.compose.panthercompose.moviesappmvi.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.compose.panthercompose.moviesappmvi.data.models.Movie
import com.compose.panthercompose.pokemon.presentation.theme.green200
import com.compose.panthercompose.pokemon.presentation.theme.green700
import com.compose.panthercompose.pokemon.presentation.theme.typography

sealed class MoviesHomeInteractionEvents {
    data class OpenMovieDetail(val movie: Movie, val imageId: Int = 0) :
        MoviesHomeInteractionEvents()

    data class AddToMyWatchlist(val movie: Movie) : MoviesHomeInteractionEvents()
    data class RemoveFromMyWatchlist(val movie: Movie) : MoviesHomeInteractionEvents()
}

@Composable
fun InterestTag(text: String) {
    val tagModifier = Modifier
        .padding(4.dp)
        .clickable(onClick = {})
        .clip(RoundedCornerShape(4.dp))
        .background(green200.copy(alpha = 0.2f))
        .padding(horizontal = 8.dp, vertical = 4.dp)

    Text(
        text = text,
        color = green700,
        modifier = tagModifier,
        style = typography.body2.copy(fontWeight = FontWeight.Bold)
    )
}
