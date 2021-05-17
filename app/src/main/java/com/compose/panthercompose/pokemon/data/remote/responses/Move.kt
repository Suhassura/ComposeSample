package com.compose.panthercompose.pokemon.data.remote.responses

data class Move(
    val move: com.compose.panthercompose.pokemon.data.remote.responses.MoveX,
    val version_group_details: List<com.compose.panthercompose.pokemon.data.remote.responses.VersionGroupDetail>
)