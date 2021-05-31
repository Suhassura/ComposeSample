package com.compose.panthercompose.pokemon.presentation.ui.pokemon_list

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.compose.panthercompose.R
import com.compose.panthercompose.pokemon.data.models.PokedexListEntry
import com.compose.panthercompose.pokemon.presentation.theme.fontFamily
import com.google.accompanist.coil.rememberCoilPainter

@ExperimentalFoundationApi
@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    //systemUiController.setStatusBarColor(MaterialTheme.colors.onPrimary)

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 65.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                viewModel.searchPokemonList(it)
            }
            Spacer(modifier = Modifier.height(8.dp))
            List(navController = navController)
        }
    }
}

//Top Search Bar
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = fontFamily
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it != FocusState.Active && text.isEmpty()
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

//Pokemon List
@ExperimentalFoundationApi
@Composable
fun List(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

    val pokemonList by remember { viewModel.pokemonList }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }


    val animatedProgress = remember { Animatable(initialValue = 300f) }
    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 0f,
            animationSpec = tween(1000, easing = FastOutSlowInEasing)
        )
    }


    LazyColumn(
        modifier = Modifier
            .graphicsLayer(translationX = animatedProgress.value)
    ) {
        itemsIndexed(pokemonList) { _, value ->
            PokedexEntry(
                entry = value,
                navController = navController
            )
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadPokemonPaginated()
            }
        }
    }
}

//Pokemon List Item
@Composable
fun PokedexEntry(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            )
            .shadow(5.dp, RoundedCornerShape(20.dp, 0.dp, 20.dp, 0.dp))
            .clip(RoundedCornerShape(20.dp, 0.dp, 20.dp, 0.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${dominantColor.toArgb()}/${entry.pokemonName}"
                )

            }
    ) {
        Row {
            Image(
                painter = rememberCoilPainter(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data(entry.imageUrl)
                        .target {
                            viewModel.calcDominantColor(it) { color ->
                                dominantColor = color
                            }
                        }.build(),
                    fadeIn = true
                ),
                contentDescription = entry.pokemonName,
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f)
                    .align(CenterVertically)
                    .padding(16.dp)
            )
            Text(
                text = entry.pokemonName,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(CenterVertically),

                )
            Text(
                text = "#${entry.number}",
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp)
                    .align(CenterVertically),

                )
        }
    }
}

//on Retry
@Composable
fun RetrySection(
    error: String,
    viewModel: PokemonListViewModel = hiltViewModel(),
    onRetry: () -> Unit

) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.loadError.value = ""
                onRetry()
            },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}
