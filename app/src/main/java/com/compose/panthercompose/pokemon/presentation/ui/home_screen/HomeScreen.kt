package com.compose.panthercompose.pokemon.presentation.ui.home_screen

import android.os.Bundle
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LibraryAdd
import androidx.compose.material.icons.outlined.MovieCreation
import androidx.compose.material.icons.outlined.OtherHouses
import androidx.compose.material.icons.outlined.Subscriptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.compose.panthercompose.movies.ui.home.*
import com.compose.panthercompose.movies.ui.trending.MovieTrendingScreen
import com.compose.panthercompose.movies.ui.watchlist.WatchlistScreen
import com.compose.panthercompose.pokemon.presentation.theme.fontFamily
import com.compose.panthercompose.pokemon.presentation.ui.pokemon_list.PokemonListScreen

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MoviesHomeViewModel = hiltViewModel()
) {
    val navType = rememberSaveable { mutableStateOf(HomeNavType.SHOWING) }

    Scaffold(bottomBar = { MoviesBottomBar(navType) }) {
        Crossfade(targetState = navType) {
            when (it.value) {
                HomeNavType.SHOWING -> MovieHomeScreen(
                    moviesHomeInteractionEvents = { interacts ->
                        handleInteractionEvents(interacts, viewModel, navController)
                    }
                )
                HomeNavType.POKEMON -> PokemonListScreen(
                    navController = navController
                )
                HomeNavType.WATCHLIST -> WatchlistScreen { interacts ->
                    handleInteractionEvents(interacts, viewModel = viewModel, navController)
                }

                HomeNavType.OTHER -> MovieTrendingScreen { interacts ->
                    handleInteractionEvents(interacts, viewModel = viewModel, navController)
                }
            }
        }
    }
}

@Composable
fun MoviesBottomBar(navType: MutableState<HomeNavType>) {
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
            selected = navType.value == HomeNavType.SHOWING,
            onClick = { navType.value = HomeNavType.SHOWING },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.Green,
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
            selected = navType.value == HomeNavType.POKEMON,
            onClick = { navType.value = HomeNavType.POKEMON },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.Green,
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
            selected = navType.value == HomeNavType.WATCHLIST,
            onClick = { navType.value = HomeNavType.WATCHLIST },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.Green,
            label = {
                Text(
                    text = "Watchlist",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.OtherHouses,
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp)
                )
            },
            selected = navType.value == HomeNavType.OTHER,
            onClick = { navType.value = HomeNavType.OTHER },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.Green,
            label = {
                Text(
                    text = "Other",
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        )
    }
}

fun handleInteractionEvents(
    interactionEvents: MoviesHomeInteractionEvents,
    viewModel: MoviesHomeViewModel,
    navController: NavController
) {
    when (interactionEvents) {
        is MoviesHomeInteractionEvents.AddToMyWatchlist -> {
            viewModel.addToMyWatchlist(interactionEvents.movie)
        }
        is MoviesHomeInteractionEvents.RemoveFromMyWatchlist -> {
            viewModel.removeFromMyWatchlist(interactionEvents.movie)
        }

        is MoviesHomeInteractionEvents.OpenMovieDetail -> {
            navController.currentBackStackEntry?.arguments =
                Bundle().apply {
                    putSerializable("movie", interactionEvents.movie)
                }
            navController.navigate("movie_details")
        }

    }
}

enum class HomeNavType {
    SHOWING, POKEMON, WATCHLIST, OTHER
}