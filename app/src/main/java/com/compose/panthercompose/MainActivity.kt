package com.compose.panthercompose

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.compose.panthercompose.pokemon.presentation.theme.PantherComposeTheme
import com.compose.panthercompose.pokemon.presentation.theme.SystemUiController
import com.compose.panthercompose.pokemon.presentation.ui.details_screen.DetailsScreen
import com.compose.panthercompose.pokemon.presentation.ui.home_screen.HomeScreen
import com.compose.panthercompose.pokemon.presentation.ui.pokemon_list.PokemonListScreen
import com.compose.panthercompose.pokemon.presentation.ui.splash_screen.SplashScreen
import com.compose.panthercompose.pokemon.utils.orDef
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("DEPRECATION")
        window.addFlags(FLAG_TRANSLUCENT_STATUS)
        setContent {
            PantherComposeTheme {
                val navController = rememberNavController()
                val systemUiController = remember { SystemUiController(window = window) }
                NavHost(
                    navController = navController,
                    startDestination = "splash_screen"
                ) {

                    composable("splash_screen") {
                        SplashScreen(
                            navController = navController,
                            systemUiController = systemUiController
                        )
                    }

                    composable("home_screen") {
                        HomeScreen(
                            navController = navController,
                            systemUiController = systemUiController
                        )
                    }

                    composable("pokemon_home_screen") {
                        PokemonListScreen(
                            navController = navController,
                            systemUiController = systemUiController
                        )
                    }

                    composable("pokemon_detail_screen/{dominantColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )
                    ) {

                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let { Color(it) } ?: Color.White
                        }

                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }

                        DetailsScreen(
                            dominantColor = dominantColor,
                            pokemonName = pokemonName.orDef(),
                            systemUiController = systemUiController
                        )
                    }


                }
            }
        }
    }
}


