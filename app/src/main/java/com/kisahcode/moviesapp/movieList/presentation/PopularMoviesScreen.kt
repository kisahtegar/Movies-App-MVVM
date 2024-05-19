package com.kisahcode.moviesapp.movieList.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kisahcode.moviesapp.movieList.presentation.components.MovieItem
import com.kisahcode.moviesapp.movieList.util.Category

/**
 * A composable function to display a grid of popular movies.
 *
 * This function checks the state of the popular movie list. If the list is empty, it displays a
 * circular progress indicator. Otherwise, it displays the movies in a grid format. When the user
 * scrolls to the end of the list, it triggers pagination to load more movies.
 *
 * @param movieListState The current state of the movie list which includes the list of popular movies and loading status.
 * @param navController The navigation controller for navigating between screens.
 * @param onEvent A callback function to handle UI events, such as pagination.
 */
@Composable
fun PopularMoviesScreen(
    movieListState: MovieListState,
    navController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit
) {

    // Check if the popular movie list is empty
    if (movieListState.popularMovieList.isEmpty()) {
        // Display a loading indicator if the list is empty
        Box(
            modifier = Modifier.fillMaxSize(),  // Fill the entire screen
            contentAlignment = Alignment.Center  // Center the content inside the box
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Display the popular movies in a grid layout if the list is not empty
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),  // Define a fixed grid with 2 columns
            modifier = Modifier.fillMaxSize(),  // Fill the entire screen
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)  // Add padding around the content
        ) {

            // Iterate through the list of popular movies
            items(movieListState.popularMovieList.size) { index ->
                // Display each movie item
                MovieItem(
                    movie = movieListState.popularMovieList[index],
                    navHostController = navController // Pass the navigation controller
                )

                // Add space between movie items
                Spacer(modifier = Modifier.height(16.dp))

                // Check if the end of the list is reached and trigger pagination if not already loading
                if (index >= movieListState.popularMovieList.size - 1 && !movieListState.isLoading) {
                    onEvent(MovieListUiEvent.Paginate(Category.POPULAR))
                }

            }
        }
    }

}