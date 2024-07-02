package com.vinay.moviemosaic.movieScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.vinay.moviemosaic.composables.RatingStars
import com.vinay.moviemosaic.data.remote.response.MovieDetails

@Composable
fun MovieDetailScreen(
    movieDetails: MovieDetails?,
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ImgBackNavSection(
                posterUrl = movieDetails?.Poster ?: "",
                navController = navController,
                imdbID = movieDetails?.imdbID ?: ""
            )
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1D1C3B))
                        .shadow(
                            2.dp,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(36.dp)
                ) {
                    Column {
                        Text(
                            text = movieDetails?.Title ?: "",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.W400,
                            lineHeight = 36.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Text(
                                text = movieDetails?.imdbRating ?: "",
                                color = Color(0xFFFFD700),
                                fontSize = 36.sp
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            RatingStars(
                                rating = movieDetails?.imdbRating?.toFloatOrNull() ?: 0f,
                                Modifier.align(Alignment.CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        GenreSection(movieDetails?.Genre ?: "")
                        Spacer(modifier = Modifier.height(50.dp))
                        Text(
                            text = movieDetails?.Plot ?: "",
                            fontSize = 18.sp, color = Color(0xFF9E9AAD),
                        )
                    }
                }
                ActorsSection(movieDetails?.Actors ?: "")
            }
        }
    }
}

@Composable
fun ImgBackNavSection(
    posterUrl: String,
    navController: NavController,
    imdbID: String
) {
    Box(modifier = Modifier.fillMaxHeight(0.45f)) {
        SubcomposeAsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(posterUrl)
                .build(),
            contentDescription = imdbID,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(50.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }

}

@Composable
fun GenreSection(genres: String) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color = Color(0xFF3A3B1C))
            .height(30.dp)
    ) {
        Row {
            val genreList = genres.split(",")
            genreList.forEach {
                Text(
                    text = it,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(
                        start = 15.dp,
                        end = 15.dp,
                        top = 3.dp
                    )
                )
            }
        }
    }

}

@Composable
fun ActorsSection(actors: String = "") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1D1C3B))
            .shadow(
                2.dp,
                RoundedCornerShape(5.dp)
            )
            .padding(36.dp)
    ) {
        Column {
            Text(
                text = "Actors  ",

                fontSize = 18.sp
            )
            Text(
                text = actors,
                fontSize = 14.sp,
                color = Color(0xFF9E9AAD),
            )
        }
    }
}