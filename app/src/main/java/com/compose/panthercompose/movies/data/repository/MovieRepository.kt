package com.compose.panthercompose.movies.data.repository

import androidx.lifecycle.LiveData
import com.compose.panthercompose.movies.data.api.MovieApi
import com.compose.panthercompose.movies.data.db.GenreDao
import com.compose.panthercompose.movies.data.db.MoviesDao
import com.compose.panthercompose.movies.data.models.Genre
import com.compose.panthercompose.movies.data.models.Movie
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityScoped
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val moviesDao: MoviesDao,
    private val genreDao: GenreDao
) {
    fun getNowShowing(): Flow<List<Movie>> = flow {
        val response = movieApi.getMovies(1)
        if (response.isSuccessful) {
            emit(response.body()?.movies ?: emptyList<Movie>())
        } else {
            println("empty list emitted")
            emit(emptyList<Movie>())
        }

    }.catch {
        emit(emptyList<Movie>())
    }.flowOn(Dispatchers.IO)

    fun getSimilarMovies(movieId: String): Flow<List<Movie>> = flow {
        val response = movieApi.getSimilarMovies(movieId)
        if (response.isSuccessful) {
            emit(response.body()?.movies ?: emptyList<Movie>())
        } else {
            emit(emptyList<Movie>())
        }

    }.catch {
        emit(emptyList<Movie>())
    }.flowOn(Dispatchers.IO)

    fun getMyWatchlist(): LiveData<List<Movie>> {
        return moviesDao.getMyWatchlist()
    }

    fun addToMyWatchlist(movie: Movie) {
        moviesDao.addToWatchList(movie)
    }

    fun removeFromMyWatchlist(movie: Movie) {
        moviesDao.removeFromMYWatchlist(movie)
    }

    fun getGenres(): LiveData<List<Genre>> = genreDao.getAllGenres()

    fun fetchAndSaveGenresToDatabase(): Flow<List<Genre>> = flow {
        val response = movieApi.getGenres()
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.genres.isNotEmpty()) {
                    genreDao.insertAllGenres(it.genres)
                }
            }
        } else {
            emit(emptyList<Genre>())
        }
    }.catch {
        emit(emptyList<Genre>())
    }.flowOn(Dispatchers.IO)
}