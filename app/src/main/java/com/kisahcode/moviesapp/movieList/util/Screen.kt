package com.kisahcode.moviesapp.movieList.util

/**
 * A sealed class representing different screens in the app, each identified by a unique route.
 * This class is used for navigation within the app.
 *
 * @param rout The route or identifier for the screen.
 */
sealed class Screen(val rout: String) {

    /**
     * Represents the home screen.
     */
    data object Home : Screen("main")

    /**
     * Represents the screen displaying popular movies.
     */
    data object PopularMovieList : Screen("popularMovie")

    /**
     * Represents the screen displaying upcoming movies.
     */
    data object UpcomingMovieList : Screen("upcomingMovie")

    /**
     * Represents the details screen for a specific movie.
     */
    data object Details : Screen("details")

}