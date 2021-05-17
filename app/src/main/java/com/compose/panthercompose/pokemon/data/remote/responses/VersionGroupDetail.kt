package com.compose.panthercompose.pokemon.data.remote.responses

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: com.compose.panthercompose.pokemon.data.remote.responses.MoveLearnMethod,
    val version_group: com.compose.panthercompose.pokemon.data.remote.responses.VersionGroup
)