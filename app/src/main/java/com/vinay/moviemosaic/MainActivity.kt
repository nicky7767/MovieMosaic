package com.vinay.moviemosaic


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.vinay.moviemosaic.data.remote.response.MovieDetails
import com.vinay.moviemosaic.movieScreens.MovieDetailScreen
import com.vinay.moviemosaic.movieScreens.MovieListScreen
import com.vinay.moviemosaic.navigation.Screen
import com.vinay.moviemosaic.ui.theme.MovieMosaicTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieMosaicTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.MovieListScreen.route
                ) {
                    composable(Screen.MovieListScreen.route) {
                        MovieListScreen(navController = navController)
                    }
                    composable(
                        Screen.MovieDetailScreen.route,
                        arguments = listOf(navArgument("movieDetails") {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        val movieDetailsJson = backStackEntry.arguments?.getString("movieDetails")
                        val movieDetails =
                            Gson().fromJson(movieDetailsJson, MovieDetails::class.java)
                        MovieDetailScreen(
                            movieDetails = movieDetails,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

