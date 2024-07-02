package com.vinay.moviemosaic.movieScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.vinay.moviemosaic.composables.RatingStars
import com.vinay.moviemosaic.data.remote.response.MovieDetails
import com.vinay.moviemosaic.navigation.Screen
import com.vinay.moviemosaic.util.Resource


@Composable
fun MovieListScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "MovieMosaic",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            MovieList(navController)
        }
    }
}

@Composable
fun MovieList(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val moviesList by viewModel.moviesList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val endReached by viewModel.endReached.collectAsState()
    LazyColumn {
        items(moviesList) { movie ->
            MovieEntry(movieDetails = movie) {
                navController.navigate(Screen.MovieDetailScreen.createRoute(movie.data))
            }
        }
    }
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
    } else if (!endReached) {
        LaunchedEffect(Unit) {
            viewModel.fetchMoviesPaginated()
        }
    }
}


@Composable
fun MovieEntry(
    movieDetails: Resource<MovieDetails>,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1D1C3B))
            .shadow(
                2.dp,
                RoundedCornerShape(5.dp)
            )
            .clickable(onClick = onClick)

    ) {
        Row {
            SubcomposeAsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(movieDetails.data?.Poster)
                    .build(),
                contentDescription = movieDetails.data?.imdbID,
            )
            Column(
                modifier = Modifier.padding(
                    start = 30.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            ) {
                Text(
                    text = movieDetails.data?.Title ?: "",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W400,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Text(
                        text = "Duration  ",
                        color = Color(0xFF9E9AAD),
                        fontSize = 18.sp
                    )
                    Text(
                        text = movieDetails.data?.Runtime ?: "",
                        fontSize = 14.sp
                    )
                }
                Row {
                    Text(
                        text = "Year  ",
                        color = Color(0xFF9E9AAD),
                        fontSize = 18.sp
                    )
                    Text(text = movieDetails.data?.Year ?: "", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = movieDetails.data?.imdbRating ?: "",
                        color = Color(0xFFFFD700),
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    RatingStars(rating = movieDetails.data?.imdbRating?.toFloatOrNull() ?: 0f)
                }

            }
        }
    }
}