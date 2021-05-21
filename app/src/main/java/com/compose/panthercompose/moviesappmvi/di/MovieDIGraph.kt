package com.compose.panthercompose.moviesappmvi.di

import android.annotation.SuppressLint
import com.compose.panthercompose.PantherComposeApplication
import com.compose.panthercompose.moviesappmvi.data.api.MovieApi
import com.compose.panthercompose.moviesappmvi.data.db.MoviesDatabase
import com.compose.panthercompose.moviesappmvi.data.repository.MovieRepository
import com.compose.panthercompose.moviesappmvi.data.repository.MoviesLanesRepository
import com.compose.panthercompose.pokemon.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@SuppressLint("StaticFieldLeak")
object MovieDIGraph {

    private val context = PantherComposeApplication.applicationContext()

    //create room db
    private val movieDatabase = MoviesDatabase.getInstance(context)


    private val movieDao = movieDatabase.moviesDao()
    private val genreDao = movieDatabase.genreDao()


    @Singleton
    @Provides
    fun movieRepository(
        movieApi: MovieApi
    ) = MovieRepository(movieApi = movieApi, moviesDao = movieDao, genreDao = genreDao)


    @Singleton
    @Provides
    fun moviesLanesRepository(
        movieApi: MovieApi
    ) = MoviesLanesRepository(movieApi = movieApi)

    @Singleton
    @Provides
    fun provideMovieApi(): MovieApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.MOVIE_BASE_URL)
            .build()
            .create(MovieApi::class.java)
    }

}