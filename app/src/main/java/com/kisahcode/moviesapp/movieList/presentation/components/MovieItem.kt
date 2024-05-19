package com.kisahcode.moviesapp.movieList.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.kisahcode.moviesapp.movieList.data.remote.MovieApi
import com.kisahcode.moviesapp.movieList.domain.model.Movie
import com.kisahcode.moviesapp.movieList.util.RatingBar
import com.kisahcode.moviesapp.movieList.util.Screen
import com.kisahcode.moviesapp.movieList.util.getAverageColor

/**
 * A composable function to display a movie item with an image, title, and rating.
 * The item is clickable and navigates to the movie details screen.
 *
 * @param movie The movie object containing details to be displayed.
 * @param navHostController The navigation controller for navigating to the details screen.
 */
@Composable
fun MovieItem(
    movie: Movie,
    navHostController: NavHostController
) {

    // Remember the state of the image being loaded
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + movie.backdrop_path) // URL to fetch the movie's backdrop image
            .size(Size.ORIGINAL) // Request the original image size
            .build()
    ).state

    // Default color for the background gradient
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominantColor by remember {
        mutableStateOf(defaultColor)
    }

    Column(
        modifier = Modifier
            .wrapContentHeight() // Adjust height to wrap the content
            .width(200.dp) // Set fixed width for the item
            .padding(8.dp) // Apply padding around the item
            .clip(RoundedCornerShape(28.dp)) // Clip the item to a rounded rectangle shape
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        dominantColor
                    )
                )
            )
            .clickable {
                // Navigate to the movie details screen
                navHostController.navigate(Screen.Details.rout + "/${movie.id}")
            }
    ) {

        // Display an error icon if the image fails to load
        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    // Set size for the icon
                    modifier = Modifier.size(70.dp),
                    // Use a built-in icon to represent image not supported
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = movie.title
                )
            }
        }

        // Display the image if it loads successfully
        if (imageState is AsyncImagePainter.State.Success) {
            dominantColor = getAverageColor(
                imageBitmap = imageState.result.drawable.toBitmap().asImageBitmap()
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()  // Fill the width of the parent
                    .padding(6.dp)  // Apply padding inside the image container
                    .height(250.dp)  // Set fixed height for the image
                    .clip(RoundedCornerShape(22.dp)),  // Clip the image to a rounded rectangle shape
                painter = imageState.painter,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Display the movie title
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),  // Apply padding to the text
            text = movie.title,  // Set the movie title as the text
            color = Color.White,  // Set text color to white
            fontSize = 15.sp,  // Set font size for the text
            maxLines = 1  // Limit the text to one line
        )

        // Display the movie rating using a custom RatingBar composable
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 12.dp, top = 4.dp)
        ) {
            RatingBar(
                starsModifier = Modifier.size(18.dp),  // Set size for the stars in the rating bar
                rating = movie.vote_average / 2  // Convert rating to a 5-star scale
            )

            Text(
                modifier = Modifier.padding(start = 4.dp),  // Apply padding to the text
                text = movie.vote_average.toString().take(3),  // Display the rating value with up to 3 characters
                color = Color.LightGray,  // Set text color to light gray
                fontSize = 14.sp,  // Set font size for the text
                maxLines = 1  // Limit the text to one line
            )
        }
    }
}