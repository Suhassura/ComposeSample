package com.compose.panthercompose.movies.ui.details

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.compose.panthercompose.movies.data.models.Movie
import com.compose.panthercompose.movies.ui.home.InterestTag
import com.compose.panthercompose.movies.utils.generateDominantColorState
import com.compose.panthercompose.movies.utils.verticalGradientBackground
import com.compose.panthercompose.pokemon.presentation.theme.fontFamily
import com.compose.panthercompose.pokemon.presentation.theme.graySurface
import com.compose.panthercompose.pokemon.presentation.theme.typography
import java.util.*

@Composable
fun MovieDetailContent(
    imageId: Int = 0,
    navController: NavController,
) {
    val movie = navController.previousBackStackEntry
        ?.arguments?.getSerializable("movie") as Movie

    val expand = remember { mutableStateOf(false) }
    val viewModel: MovieDetailViewModel = hiltViewModel()
    var dominantColors = listOf(graySurface, Color.Black)

    if (imageId != 0) {
        val context = LocalContext.current
        val currentBitmap = ImageBitmap.imageResource(context.resources, imageId)

        val swatch = generateDominantColorState(currentBitmap.asAndroidBitmap())
        dominantColors = listOf(Color(swatch.rgb), Color.Black)
    }

    LazyColumn(
        modifier = Modifier
            .verticalGradientBackground(dominantColors)
            .padding(
                animateDpAsState(
                    if (expand.value) 1.dp else 120.dp,
                    tween(350)
                ).value
            )
    ) {
        item {
            val painter = rememberCoilPainter(
                request = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"
            )
            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .height(
                        600.dp
                    )
                    .fillMaxWidth(),
            )
            when (painter.loadState) {
                is ImageLoadState.Success -> expand.value = true
                else -> expand.value = false
            }
        }
        item {
            Column(modifier = Modifier.background(MaterialTheme.colors.onSurface)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = movie.title,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Green,
                        style = typography.h6
                    )
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.LibraryAdd,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
                GenreSection(viewModel, movie.genre_ids)
                Text(
                    text = "Release: ${movie.release_date}",
                    color = Color.Red,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = typography.h6.copy(fontSize = 12.sp)
                )
                Text(
                    text = "PG13  ???  ${movie.vote_average}/10",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Red,
                )
                Text(
                    text = movie.overview,
                    modifier = Modifier
                        .padding(8.dp),
                    fontSize = 12.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Red,
                )
                Spacer(modifier = Modifier.height(20.dp))
                SimilarMoviesSection(movie, viewModel)
                Spacer(modifier = Modifier.height(50.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Get Tickets".capitalize(Locale.ROOT),
                        fontSize = 20.sp,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

    }
}


@Composable
fun SimilarMoviesSection(currentMovie: Movie?, viewModel: MovieDetailViewModel) {
    viewModel.getSimilarMovies(currentMovie?.id.toString())
    val similarMovies by viewModel.similarMoviesLiveData.observeAsState()


    similarMovies?.let { movies ->
        Text(
            text = "Similar Movies",
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color.Green,
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp)
        )
        LazyRow {
            items(
                items = movies,
                itemContent = { movie: Movie ->
                    Image(
                        painter = rememberCoilPainter(
                            request = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .width(200.dp)
                            .height(300.dp)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                })
        }
    }
}

@Suppress("NAME_SHADOWING")
@Composable
fun GenreSection(viewModel: MovieDetailViewModel, movieGenreIds: List<Int>?) {
    movieGenreIds?.let { movieGenreIds ->
        val genres by viewModel.genresLiveData.observeAsState(emptyList())
        val movieGenres = genres.filter {
            movieGenreIds.contains(it.id)
        }.take(3)
        Row {
            movieGenres.forEach {
                InterestTag(text = it.name)
            }
        }
    }
}
