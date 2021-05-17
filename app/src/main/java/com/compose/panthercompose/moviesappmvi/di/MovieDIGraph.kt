package com.compose.panthercompose.moviesappmvi.di

import android.annotation.SuppressLint
import com.compose.panthercompose.PantherComposeApplication
import com.compose.panthercompose.moviesappmvi.data.api.MovieApi
import com.compose.panthercompose.moviesappmvi.data.db.MoviesDatabase
import com.compose.panthercompose.moviesappmvi.data.repository.MovieRepositoryImpl
import com.compose.panthercompose.moviesappmvi.data.repository.MoviesLaneRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@SuppressLint("StaticFieldLeak")
object MovieDIGraph {


    private val context = PantherComposeApplication.applicationContext()

    // create retrofit
    private val movieApi = MovieApi.invoke()


    //create room db
    private val movieDatabase = MoviesDatabase.getInstance(context)
    private val movieDao = movieDatabase.moviesDao()
    private val genreDao = movieDatabase.genreDao()

    //pass dependencies to repository
    val movieRepository by lazy {
        MovieRepositoryImpl(movieApi, movieDao, genreDao)
    }

    val moviesLanesRepository by lazy {
        MoviesLaneRepositoryImpl(movieApi)
    }

    val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main

    val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

}