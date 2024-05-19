package com.kisahcode.moviesapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kisahcode.moviesapp.movieList.util.Screen
import com.kisahcode.moviesapp.ui.theme.MoviesAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main entry point of the application. This activity hosts the entire app and
 * navigates between different screens using Jetpack Navigation.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Set up the theme for the entire app
            MoviesAppTheme {
                // Set the color of the system bars to the inverse color of the surface color
                SetBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Set up navigation using Jetpack Navigation
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.rout
                    ) {
                        // Define composable destinations for each screen

                        composable(Screen.Home.rout) {
                            HomeScreen(navController)
                        }

                        composable(
                            Screen.Details.rout + "/{movieId}",
                            arguments = listOf(
                                navArgument("movieId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
//                            DetailsScreen(backStackEntry)
                        }
                    }

                }
            }
        }
    }

    /**
     * Composable function to set the color of the system bars.
     * It ensures that the color of the system bars matches the provided color.
     *
     * @param color The color to set for the system bars.
     */
    @Composable
    private fun SetBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1 = color) {
            systemUiController.setSystemBarsColor(color)
        }
    }
}