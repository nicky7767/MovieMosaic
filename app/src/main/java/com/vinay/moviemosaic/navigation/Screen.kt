package com.vinay.moviemosaic.navigation

import android.net.Uri
import com.google.gson.Gson
import com.vinay.moviemosaic.data.remote.response.MovieDetails

sealed class Screen(val route: String) {
    object MovieListScreen : Screen("movie_list_screen")
    object MovieDetailScreen : Screen("movie_detail_screen/{movieDetails}") {
        fun createRoute(movieDetails: MovieDetails?) = "movie_detail_screen/${Uri.encode(Gson().toJson(movieDetails))}"
    }

}