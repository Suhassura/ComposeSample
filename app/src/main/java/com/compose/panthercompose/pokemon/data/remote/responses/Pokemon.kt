package com.compose.panthercompose.pokemon.data.remote.responses

data class Pokemon(
    val abilities: List<com.compose.panthercompose.pokemon.data.remote.responses.Ability>,
    val base_experience: Int,
    val forms: List<com.compose.panthercompose.pokemon.data.remote.responses.Form>,
    val game_indices: List<com.compose.panthercompose.pokemon.data.remote.responses.GameIndice>,
    val height: Int,
    val held_items: List<com.compose.panthercompose.pokemon.data.remote.responses.HeldItem>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<com.compose.panthercompose.pokemon.data.remote.responses.Move>,
    val name: String,
    val order: Int,
    val past_types: List<Any>,
    val species: com.compose.panthercompose.pokemon.data.remote.responses.Species,
    val sprites: com.compose.panthercompose.pokemon.data.remote.responses.Sprites,
    val stats: List<com.compose.panthercompose.pokemon.data.remote.responses.Stat>,
    val types: List<com.compose.panthercompose.pokemon.data.remote.responses.Type>,
    val weight: Int
)