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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
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


    val movieDao = movieDatabase.moviesDao()
    val genreDao = movieDatabase.genreDao()


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

    private val requestInterceptor = Interceptor { chain ->
        val url = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", "852eb333fbdf1f20f7da454df993da34")
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideMovieApi(): MovieApi {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.MOVIE_BASE_URL)
            .build()
            .create(MovieApi::class.java)
    }

}