package com.kisahcode.moviesapp.movieList.data.remote.response

/**
 * Data Transfer Object (DTO) representing a paginated list of movies as retrieved from the remote API.
 *
 * @property page The current page number of the movie list.
 * @property results The list of movies for the current page, represented by [MovieDto] objects.
 * @property total_pages The total number of pages available.
 * @property total_results The total number of movie results available.
 */
data class MovieListDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)