package com.compose.panthercompose.pokemon.repository

import com.compose.panthercompose.pokemon.data.remote.responses.Pokemon
import com.compose.panthercompose.pokemon.data.remote.responses.PokemonList
import com.compose.panthercompose.pokemon.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: com.compose.panthercompose.pokemon.data.remote.PokeApi
) {

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(
                limit = limit,
                offset = offset
            )
        } catch (e: Exception) {
            return Resource.Error(message = "Unknown Error")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(
                name = pokemonName
            )
        } catch (e: Exception) {
            return Resource.Error(message = "Unknown Error")
        }
        return Resource.Success(response)
    }


}