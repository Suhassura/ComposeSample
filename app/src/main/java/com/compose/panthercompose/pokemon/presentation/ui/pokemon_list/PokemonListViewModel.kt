package com.compose.panthercompose.pokemon.presentation.ui.pokemon_list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.compose.panthercompose.pokemon.repository.PokemonRepository
import com.compose.panthercompose.pokemon.utils.Resource
import com.compose.panthercompose.pokemon.utils.orDef
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    var pokemonList = mutableStateOf<List<com.compose.panthercompose.pokemon.data.models.PokedexListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var cachedPokemonList = listOf<com.compose.panthercompose.pokemon.data.models.PokedexListEntry>()
    private var isSearchStarted = true
    var isSearching = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if (isSearchStarted) {
            pokemonList.value
        } else {
            cachedPokemonList
        }

        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                isSearching.value = false
                isSearchStarted = true
                return@launch
            }

            val results = listToSearch.filter {
                it.pokemonName.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim()
            }

            if (isSearchStarted) {
                cachedPokemonList = pokemonList.value
                isSearchStarted = false
            }

            pokemonList.value = results
            isSearching.value = true
        }
    }

    fun loadPokemonPaginated() {
        viewModelScope.launch {
            isLoading.value = true

            when (val result = repository.getPokemonList(700, 0)) {
                is Resource.Success -> {

                    val pokedexEntries = result.data?.results?.mapIndexed { _, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        com.compose.panthercompose.pokemon.data.models.PokedexListEntry(
                            entry.name.capitalize(
                                Locale.ROOT
                            ), url, number.toInt()
                        )
                    }
                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value = pokedexEntries.orEmpty()
                }
                is Resource.Error -> {
                    loadError.value = result.message.orDef()
                    isLoading.value = false
                }
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}
