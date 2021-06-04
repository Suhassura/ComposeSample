package com.compose.panthercompose.movies.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.compose.panthercompose.movies.data.models.Movie
import com.compose.panthercompose.movies.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
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