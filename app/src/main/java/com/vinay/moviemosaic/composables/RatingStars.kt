package com.vinay.moviemosaic.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.vinay.moviemosaic.R

@Composable
fun RatingStars(rating: Float = 0f, modifier: Modifier = Modifier) {
    val maxStars = 5
    val scaledRating = rating / 2
    val filledStars = scaledRating.toInt()
    val halfStar = if (scaledRating % 1 >= 0.5) 1 else 0
    val emptyStars = maxStars - filledStars - halfStar

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_star_24),
                contentDescription = "filled star",
                tint = Color(0xFFFFD700),
            )
        }
        if (halfStar == 1) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_star_half_24),
                contentDescription = "Half Star",
                tint = Color(0xFFFFD700),
            )
        }
        repeat(emptyStars) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_star_border_24),
                contentDescription = "Empty Star",
                tint = Color.Gray,
            )
        }
    }
}