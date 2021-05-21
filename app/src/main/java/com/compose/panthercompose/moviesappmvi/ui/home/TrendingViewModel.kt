package com.compose.panthercompose.moviesappmvi.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.panthercompose.moviesappmvi.data.models.Movie
import com.compose.panthercompose.moviesappmvi.data.repository.MoviesLanesRepository
import com.compose.panthercompose.moviesappmvi.di.MovieDIGraph
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
            // TODO create new model for TV showsq
//            moviesLanesRepository.getTopRatedTVShwos().collect {
//                topRatedTVShows.value = it
//            }
//            moviesLanesRepository.getTrendingTVShows().collect {
//                trendingTVShowsLiveData.value = it
//            }
        }
    }
}