package com.compose.panthercompose.pokemon.data.remote.responses

data class Ability(
    val ability: com.compose.panthercompose.pokemon.data.remote.responses.AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)