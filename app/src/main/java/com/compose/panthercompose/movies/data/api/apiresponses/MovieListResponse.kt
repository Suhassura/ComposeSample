package com.compose.panthercompose.movies.data.api.apiresponses

import com.google.gson.annotations.SerializedName
import com.compose.panthercompose.movies.data.models.Movie

data class MovieListResponse(
    @SerializedName("pages") val pages: Int,
    @SerializedName("results") val movies: List<Movie>
)