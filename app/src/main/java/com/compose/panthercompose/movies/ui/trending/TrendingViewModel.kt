package com.compose.panthercompose.movies.ui.trending

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.panthercompose.movies.data.models.Movie
import com.compose.panthercompose.movies.data.repository.MoviesLanesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val moviesLanesRepository: MoviesLanesRepository
) : ViewModel() {
    val trendingMoviesLiveData = MutableLiveData<List<Movie>>()
    val popularMoviesLiveData = MutableLiveData<List<Movie>>()
    val topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedTVShows = MutableLiveData<List<Movie>>()
    val trendingTVShowsLiveData = MutableLiveData<List<Movie>>()

    init {
        viewModelScope.launch {
            moviesLanesRepository.getTrendingMovies().collect {
                trendingMoviesLiveData.value = it
            }
            moviesLanesRepository.getPopularMovies().collect {
                popularMoviesLiveData.value = it
            }
            moviesLanesRepository.getTopRatedMovies().collect {
                topRatedMovies.value = it
            }

            moviesLanesRepository.getTopRatedTVShwos().collect {
                topRatedTVShows.value = it
            }
            moviesLanesRepository.getTrendingTVShows().collect {
                trendingTVShowsLiveData.value = it
            }
        }
    }
}