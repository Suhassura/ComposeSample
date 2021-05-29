package com.compose.panthercompose.pokemon.presentation.ui.home_screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LibraryAdd
import androidx.compose.material.icons.outlined.MovieCreation
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.NavController
import com.compose.panthercompose.moviesappmvi.ui.home.MovieHomeScreen
import com.compose.panthercompose.moviesappmvi.ui.home.MoviesHomeInteractionEvents
import com.compose.panthercompose.moviesappmvi.ui.home.MoviesHomeViewModel
import com.compose.panthercompose.moviesappmvi.ui.home.WatchlistScreen
import com.compose.panthercompose.pokemon.presentation.theme.fontFamily
import com.compose.panthercompose.pokemon.presentation.ui.pokemon_list.PokemonListScreen

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MoviesHomeViewModel = hiltNavGraphViewModel()
) {
    val navType = rememberSaveable { mutableStateOf(MovieNavType.SHOWING) }

    Scaffold(bottomBar = { MoviesBottomBar(navType) }) {
        Crossfade(targetState = navType) {
            when (it.value) {
                MovieNavType.SHOWING -> MovieHomeScreen(
                    moviesHomeInteractionEvents = { interacts ->
                        handleInteractionEvents(interacts, viewModel)
                    }
                )
                MovieNavType.TRENDING -> PokemonListScreen(
                    navController = navController
                )
                MovieNavType.WATCHLIST -> WatchlistScreen { interacts ->
                    handleInteractionEvents(interacts, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MoviesBottomBar(navType: MutableState<MovieNavType>) {
    val bottomNavBackground =
        if (isSystemInDarkTheme()) Color.Black else MaterialTheme.colors.onPrimary

    BottomNavigation(
        backgroundColor = bottomNavBackground,
        modifier = Modifier.height(60.dp),
        elevation = 8.dp,
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.MovieCreation,
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp)
                )
            },
            selected = navType.value == MovieNavType.SHOWING,
            onClick = { navType.value = MovieNavType.SHOWING },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.Black,
            label = {
                Text(
                    text = "Showing",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            },
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Subscriptions,
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp)
                )
            },
            selected = navType.value == MovieNavType.TRENDING,
            onClick = { navType.value = MovieNavType.TRENDING },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.Black,
            label = {
                Text(
                    text = "Pokemon",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.LibraryAdd,
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp)
                )
            },
            selected = navType.value == MovieNavType.WATCHLIST,
            onClick = { navType.value = MovieNavType.WATCHLIST },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.Black,
            label = {
                Text(
                    text = "Watchlist",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        )
    }
}

fun handleInteractionEvents(
    interactionEvents: MoviesHomeInteractionEvents,
    viewModel: MoviesHomeViewModel
) {
    when (interactionEvents) {
        is MoviesHomeInteractionEvents.AddToMyWatchlist -> {
            viewModel.addToMyWatchlist(interactionEvents.movie)
        }
        is MoviesHomeInteractionEvents.RemoveFromMyWatchlist -> {
            viewModel.removeFromMyWatchlist(interactionEvents.movie)
        }
        else -> {

        }
    }
}

enum class MovieNavType {
    SHOWING, TRENDING, WATCHLIST
}