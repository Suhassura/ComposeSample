package com.compose.panthercompose.movies.ui.trending

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.panthercompose.movies.ui.trending.MoviesLaneItem
import com.compose.panthercompose.movies.ui.home.MoviesHomeInteractionEvents
import com.compose.panthercompose.movies.utils.horizontalGradientBackground
import com.compose.panthercompose.movies.utils.sample_data.DataProvider

@Composable
fun MovieTrendingScreen(moviesHomeInteractionEvents: (MoviesHomeInteractionEvents) -> Unit) {
    val surfaceGradient = DataProvider.SurfaceGradient(isSystemInDarkTheme())
    val statusBarHeight = 32.dp
    val viewModel: TrendingViewModel = hiltViewModel()
    val showLoading = remember { mutableStateOf(true) }
    val listOfSections = listOf(
        "Trending this week",
        "Popular this week",
        "Top rated movies",
        "Trending TV shows",
        "Top rated TV shows",
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .horizontalGradientBackground(surfaceGradient)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(statusBarHeight))

        if (showLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        listOfSections.forEach {
            DynamicSection(it, viewModel, showLoading, moviesHomeInteractionEvents)
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun DynamicSection(
    type: String,
    viewModel: TrendingViewModel,
    showLoading: MutableState<Boolean>,
    moviesHomeInteractionEvents: (MoviesHomeInteractionEvents) -> Unit
) {
    val movies by when (type) {
        "Trending this week" -> viewModel.trendingMoviesLiveData.observeAsState(emptyList())
        "Popular this week" -> viewModel.popularMoviesLiveData.observeAsState(emptyList())
        "Trending TV shows" -> viewModel.trendingTVShowsLiveData.observeAsState(emptyList())
        "Top rated movies" -> viewModel.topRatedMovies.observeAsState(emptyList())
        "Top rated TV shows" -> viewModel.topRatedTVShows.observeAsState(emptyList())
        else -> viewModel.trendingMoviesLiveData.observeAsState(emptyList())
    }
    if (movies.isNotEmpty()) {
        showLoading.value = false
        MoviesLaneItem(movies = movies, title = type) { movie ->
            moviesHomeInteractionEvents(
                MoviesHomeInteractionEvents.OpenMovieDetail(movie = movie)
            )
        }
    }
}