package com.compose.panthercompose.movies.ui.home

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
class MoviesHomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val nowShowingLiveData = MutableLiveData<List<Movie>>()
    val errorLiveData = MutableLiveData<String>()

    //live data to read room database
    val genresLiveData = liveData(Dispatchers.IO) {
        emitSource(movieRepository.getGenres())
    }
    val myWatchlist = liveData(Dispatchers.IO) {
        emitSource(movieRepository.getMyWatchlist())
    }

    init {
        fetchMovies()
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMyWatchlist()
        }
    }

    fun fetchMovies() {
        viewModelScope.launch {
            movieRepository.getNowShowing()
                .collect { movies ->
                    if (movies.isNotEmpty()) {
                        nowShowingLiveData.value = movies
                    } else {
                        errorLiveData.value = "Failed to load movies"
                    }
                }
            movieRepository.fetchAndSaveGenresToDatabase().collect { }
        }
    }

    fun addToMyWatchlist(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        movieRepository.addToMyWatchlist(movie)
    }

    fun removeFromMyWatchlist(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        movieRepository.removeFromMyWatchlist(movie)
    }

}