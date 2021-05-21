package com.compose.panthercompose.moviesappmvi.data.api

import com.compose.panthercompose.moviesappmvi.data.api.apiresponses.GenreApiResponse
import com.compose.panthercompose.moviesappmvi.data.api.apiresponses.MovieListResponse
import com.compose.panthercompose.moviesappmvi.data.models.Movie
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/now_playing")
    suspend fun getMovies(@Query("page") page: Int): Response<MovieListResponse>

    @GET("movie/{movieId}/similar")
    suspend fun getSimilarMovies(@Path("movieId") movieId: String): Response<MovieListResponse>

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(): Response<MovieListResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieListResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MovieListResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(): Response<MovieListResponse>

    @GET("trending/tv/week")
    suspend fun getTrendingTVShows(): Response<MovieListResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") movieId: String): Response<Movie>

    @GET("genre/movie/list?")
    suspend fun getGenres(): Response<GenreApiResponse>
}