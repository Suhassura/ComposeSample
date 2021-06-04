package com.compose.panthercompose.movies.data.repository

import com.compose.panthercompose.movies.data.api.MovieApi
import com.compose.panthercompose.movies.data.models.Movie
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityScoped
class MoviesLanesRepository @Inject constructor(
    private val movieApi: MovieApi
) {
    suspend fun getTrendingMovies(): Flow<List<Movie>> = flow {
        val response = movieApi.getTrendingMovies()
        if (response.isSuccessful) {
            emit(response.body()?.movies ?: emptyList<Movie>())
        } else {
            emit(emptyList<Movie>())
        }

    }.catch {
        emit(emptyList<Movie>())
    }.flowOn(Dispatchers.Default)

    suspend fun getTrendingTVShows(): Flow<List<Movie>> = flow {
        val response = movieApi.getTrendingTVShows()
        if (response.isSuccessful) {
            emit(response.body()?.movies ?: emptyList<Movie>())
        } else {
            emit(emptyList<Movie>())
        }

    }.catch {
        emit(emptyList<Movie>())
    }.flowOn(Dispatchers.Default)

    suspend fun getPopularMovies(): Flow<List<Movie>> = flow {
        val response = movieApi.getPopularMovies()
        if (response.isSuccessful) {
            emit(response.body()?.movies ?: emptyList<Movie>())
        } else {
            emit(emptyList<Movie>())
        }

    }.catch {
        emit(emptyList<Movie>())
    }.flowOn(Dispatchers.Default)

    suspend fun getTopRatedMovies(): Flow<List<Movie>> = flow {
        val response = movieApi.getTopRatedMovies()
        if (response.isSuccessful) {
            emit(response.body()?.movies ?: emptyList<Movie>())
        } else {
            emit(emptyList<Movie>())
        }

    }.catch {
        emit(emptyList<Movie>())
    }.flowOn(Dispatchers.Default)

    suspend fun getTopRatedTVShwos(): Flow<List<Movie>> = flow {
        val response = movieApi.getTopRatedTvShows()
        if (response.isSuccessful) {
            emit(response.body()?.movies ?: emptyList<Movie>())
        } else {
            emit(emptyList<Movie>())
        }

    }.catch {
        emit(emptyList<Movie>())
    }.flowOn(Dispatchers.Default)
}