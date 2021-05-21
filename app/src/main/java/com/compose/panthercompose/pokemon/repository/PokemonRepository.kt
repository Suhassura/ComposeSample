package com.compose.panthercompose.pokemon.repository

import com.compose.panthercompose.pokemon.data.remote.PokeApi
import com.compose.panthercompose.pokemon.data.remote.responses.Pokemon
import com.compose.panthercompose.pokemon.data.remote.responses.PokemonList
import com.compose.panthercompose.pokemon.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
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

   /* suspend fun getPokemonList(limit: Int, offset: Int): Flow<Resource<PokemonList>> = flow {
        val response = try {
            api.getPokemonList(
                limit = limit,
                offset = offset
            )
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unknown Error"))
        }
        emit(Resource.Success(response))
    }*/

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