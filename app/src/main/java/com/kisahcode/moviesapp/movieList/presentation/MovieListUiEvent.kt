package com.kisahcode.moviesapp.movieList.presentation

/**
 * Represents UI events for the Movie List screen.
 */
sealed interface MovieListUiEvent {

    /**
     * UI event to paginate the movie list.
     *
     * @property category The category of movies to paginate.
     */
    data class Paginate(val category: String) : MovieListUiEvent

    /**
     * UI event to navigate to another screen.
     */
    data object Navigate : MovieListUiEvent

}