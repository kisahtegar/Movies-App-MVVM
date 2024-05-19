package com.kisahcode.moviesapp.movieList.util

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Composable function to display a rating bar with stars representing the rating value.
 *
 * @param modifier Modifier for the rating bar layout.
 * @param starsModifier Modifier for individual stars.
 * @param rating The rating value, typically between 0 and 5.
 * @param stars The total number of stars in the rating bar.
 * @param starsColor The color of the stars.
 */
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starsModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {

    // Calculate the number of filled, half-filled, and unfilled stars based on the rating
    val filledStars = kotlin.math.floor(rating).toInt()
    val unfilledStars = (stars - kotlin.math.ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    // Compose a Row to display stars
    Row(modifier = modifier) {
        // Display filled stars
        repeat(filledStars) {
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = starsColor
            )
        }

        // Display half-filled star if necessary
        if (halfStar) {
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Rounded.StarHalf,
                contentDescription = null,
                tint = starsColor
            )
        }

        // Display unfilled stars
        repeat(unfilledStars) {
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Rounded.StarOutline,
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}