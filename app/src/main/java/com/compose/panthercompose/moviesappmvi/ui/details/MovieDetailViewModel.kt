package com.compose.panthercompose.moviesappmvi.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.compose.panthercompose.moviesappmvi.data.models.Movie
import com.compose.panthercompose.moviesappmvi.data.repository.MovieRepository
import com.compose.panthercompose.moviesappmvi.di.MovieDIGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieRepository: MovieRepository = MovieDIGraph.movieRepository
) : ViewModel() {

    val similarMoviesLiveData = MutableLiveData<List<Movie>>()
    val genresLiveData = liveData(Dispatchers.IO) {
        emitSource(movieRepository.getGenres())
    }

    fun getSimilarMovies(movieId: String) {
        viewModelScope.launch {
            movieRepository.getSimilarMovies(movieId)
                .collect { movies ->
                    if (movies.isNotEmpty()) {
                        similarMoviesLiveData.value = movies
                    }
                }
        }
    }
}