package com.compose.panthercompose.pokemon.data.remote.responses

data class HeldItem(
    val item: com.compose.panthercompose.pokemon.data.remote.responses.Item,
    val version_details: List<com.compose.panthercompose.pokemon.data.remote.responses.VersionDetail>
)