package com.compose.panthercompose.pokemon.presentation.ui.details_screen

import androidx.lifecycle.ViewModel
import com.compose.panthercompose.pokemon.data.remote.responses.Pokemon
import com.compose.panthercompose.pokemon.repository.PokemonRepository
import com.compose.panthercompose.pokemon.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }
}