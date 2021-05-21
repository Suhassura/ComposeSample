package com.compose.panthercompose.pokemon.di

import com.compose.panthercompose.pokemon.data.remote.PokeApi
import com.compose.panthercompose.pokemon.repository.PokemonRepository
import com.compose.panthercompose.pokemon.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api:PokeApi
    ) = PokemonRepository(api = api)

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }
}