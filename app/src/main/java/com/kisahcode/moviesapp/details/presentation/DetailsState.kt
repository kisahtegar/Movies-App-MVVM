package com.kisahcode.moviesapp.details.presentation

import com.kisahcode.moviesapp.movieList.domain.model.Movie

/**
 * A data class that represents the state of the details screen in the movie app.
 *
 * This class holds the current state of the details screen, including the loading state
 * and the movie details. It is used to manage the UI state within the details screen
 * composable.
 *
 * @property isLoading A boolean flag indicating whether the movie details are currently being loaded.
 * @property movie An optional Movie object containing the details of the movie. If null, no movie details are available.
 */
data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)