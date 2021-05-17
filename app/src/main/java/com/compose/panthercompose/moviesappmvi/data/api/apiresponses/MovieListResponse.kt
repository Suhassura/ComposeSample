package com.compose.panthercompose.moviesappmvi.data.api.apiresponses

import com.google.gson.annotations.SerializedName
import com.compose.panthercompose.moviesappmvi.data.models.Movie

data class MovieListResponse(
    @SerializedName("pages") val pages: Int,
    @SerializedName("results") val movies: List<Movie>
)