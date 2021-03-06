package com.compose.panthercompose.movies.ui.watchlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.compose.panthercompose.movies.data.models.Movie
import com.compose.panthercompose.movies.ui.home.MoviesHomeInteractionEvents
import com.compose.panthercompose.movies.ui.home.MoviesHomeViewModel
import com.compose.panthercompose.movies.utils.horizontalGradientBackground
import com.compose.panthercompose.movies.utils.sample_data.DataProvider
import com.compose.panthercompose.pokemon.presentation.theme.fontFamily
import java.util.*


@Composable
fun WatchlistScreen(moviesHomeInteractionEvents: (MoviesHomeInteractionEvents) -> Unit) {
    val surfaceGradient = DataProvider.SurfaceGradient(isSystemInDarkTheme())
    val viewModel: MoviesHomeViewModel = viewModel()
    val myWatchlist by viewModel.myWatchlist.observeAsState(emptyList())
    if (myWatchlist.isNotEmpty()) {
        Surface(modifier = Modifier.horizontalGradientBackground(surfaceGradient)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface)
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                LazyColumn {
                    itemsIndexed(
                        items = myWatchlist,
                        itemContent = { index: Int, movie: Movie ->
                            MovieWatchlistItem(
                                movie,
                                {
                                    moviesHomeInteractionEvents(
                                        MoviesHomeInteractionEvents.OpenMovieDetail(movie)
                                    )
                                },
                                {
                                    moviesHomeInteractionEvents(
                                        MoviesHomeInteractionEvents.RemoveFromMyWatchlist(movie)
                                    )
                                }
                            )
                            if (index == myWatchlist.size - 1) {
                                Spacer(modifier = Modifier.padding(30.dp))
                            }
                        })
                }
            }

        }
    } else {
        EmptyWatchlistSection()
    }
}

@Composable
fun MovieWatchlistItem(
    movie: Movie,
    onMovieSelected: () -> Unit,
    onRemoveFromWatchlist: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
            .shadow(5.dp, RoundedCornerShape(20.dp, 0.dp, 20.dp, 0.dp))
            .clip(RoundedCornerShape(20.dp, 0.dp, 20.dp, 0.dp))
    ) {
        Image(
            painter = rememberCoilPainter(
                request = "https://image.tmdb.org/t/p/original/${
                    movie
                        .backdrop_path
                }"
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clickable(onClick = onMovieSelected),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = movie.title.toUpperCase(Locale.ROOT),
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
            IconButton(
                onClick = { onRemoveFromWatchlist.invoke() },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.RemoveCircleOutline,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

    }
}

@Composable
fun EmptyWatchlistSection() {
    Column {
        Spacer(modifier = Modifier.padding(100.dp))
        Text(
            text = "Watchlist is empty",
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Please add some movies to your watchlist",
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )
    }
}