package com.compose.panthercompose.moviesappmvi.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.panthercompose.R
import com.compose.panthercompose.moviesappmvi.utils.Pager
import com.compose.panthercompose.moviesappmvi.utils.PagerState
import com.compose.panthercompose.moviesappmvi.utils.generateDominantColorState
import com.compose.panthercompose.moviesappmvi.utils.verticalGradientBackground
import com.compose.panthercompose.pokemon.presentation.theme.fontFamily
import com.compose.panthercompose.pokemon.presentation.theme.typography

@Composable
fun MovieHomeScreen(moviesHomeInteractionEvents: (MoviesHomeInteractionEvents) -> Unit) {
    MovieHomeScreenContent(moviesHomeInteractionEvents)
}

@Composable
fun MovieHomeScreenContent(moviesHomeInteractionEvents: (MoviesHomeInteractionEvents) -> Unit) {
    val imageId = remember { mutableStateOf(R.drawable.camelia) }
    val context = LocalContext.current
    val defaultBitmap =
        ImageBitmap.imageResource(context.resources, imageId.value).asAndroidBitmap()
    val currentBitmap = remember { mutableStateOf(defaultBitmap) }
    val swatch = generateDominantColorState(currentBitmap.value)
    val dominantColors = listOf(Color(swatch.rgb), Color.Black)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .verticalGradientBackground(dominantColors),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item { Spacer(modifier = Modifier.height(30.dp)) }
        item {
            Text(
                text = "Now Showing",
                color = Color.Blue,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(16.dp)
            )
        }
        item { MoviesPager(imageId, moviesHomeInteractionEvents) }
    }
}

@Composable
fun MoviesPager(
    imageId: MutableState<Int>,
    moviesHomeInteractionEvents: (MoviesHomeInteractionEvents) -> Unit
) {
    val moviesViewModel: MoviesHomeViewModel = viewModel()
    val movies by moviesViewModel.nowShowingLiveData.observeAsState(emptyList())
    val genres by moviesViewModel.genresLiveData.observeAsState(emptyList())
    val error by moviesViewModel.errorLiveData.observeAsState()

    if (movies.isNotEmpty()) {
        val pagerState: PagerState = run {
            remember {
                PagerState(0, 0, movies.size - 1)
            }
        }
        Pager(state = pagerState, modifier = Modifier.height(645.dp)) {
            val movie = movies[page]
            imageId.value = imageIds[pagerState.currentPage]
            val isSelected = pagerState.currentPage == page

            MoviePagerItem(
                movie,
                genres,
                isSelected,
                { moviesHomeInteractionEvents(MoviesHomeInteractionEvents.AddToMyWatchlist(movie)) }
            ) {
                moviesHomeInteractionEvents(
                    MoviesHomeInteractionEvents.OpenMovieDetail(movie, imageId.value)
                )
            }
        }
    } else {
        if (error.isNullOrEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.padding(24.dp)
            )
        } else {
            Column {
                Text(
                    text = error ?: "Unknown error",
                    modifier = Modifier,
                    color = MaterialTheme.colors.error
                )
                Button(onClick = {
                    moviesViewModel.errorLiveData.postValue("")
                    moviesViewModel.fetchMovies()
                }) {
                    Text(
                        text = "Retry",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 12.dp),
                        color = Color.White
                    )
                }
            }

        }
    }
}

val imageIds =
    listOf(
        R.drawable.camelia,
        R.drawable.camelia,
        R.drawable.khalid,
        R.drawable.lana,
        R.drawable.edsheeran,
        R.drawable.dualipa,
        R.drawable.sam,
        R.drawable.marsh,
        R.drawable.wolves,
        R.drawable.camelia,
        R.drawable.khalid,
        R.drawable.lana,
        R.drawable.edsheeran,
        R.drawable.dualipa,
        R.drawable.sam,
        R.drawable.marsh,
        R.drawable.wolves,
        R.drawable.camelia,
        R.drawable.khalid,
        R.drawable.lana,
        R.drawable.edsheeran,
        R.drawable.dualipa,
        R.drawable.sam,
        R.drawable.marsh,
        R.drawable.wolves,
    )