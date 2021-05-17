package com.compose.panthercompose.pokemon.presentation.ui.details_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.panthercompose.pokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

   // var pokemonList = mutableStateOf<Pokemon>()
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

     fun pokemonDetails(pokemonName:String){
         viewModelScope.launch (Dispatchers.IO){
             repository.getPokemonInfo(pokemonName)
         }
     }
}