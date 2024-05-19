package com.kisahcode.moviesapp.movieList.presentation

import com.kisahcode.moviesapp.movieList.domain.model.Movie

/**
 * Represents the state of the Movie List screen.
 *
 * @property isLoading Indicates if data is currently being loaded.
 * @property popularMovieListPage The page number of the popular movie list.
 * @property upcomingMovieListPage The page number of the upcoming movie list.
 * @property isCurrentPopularScreen Indicates if the current screen is the popular movie list screen.
 * @property popularMovieList The list of popular movies.
 * @property upcomingMovieList The list of upcoming movies.
 */
data class MovieListState(
    val isLoading: Boolean = false,
    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,
    val isCurrentPopularScreen: Boolean = true,
    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList()
)