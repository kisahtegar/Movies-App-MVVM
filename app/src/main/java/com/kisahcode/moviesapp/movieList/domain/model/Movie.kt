package com.kisahcode.moviesapp.movieList.domain.model

/**
 * Domain model representing a movie. This class is used within the app's business logic and domain layer.
 *
 * @property adult Indicates if the movie is for adults only.
 * @property backdrop_path URL path to the backdrop image of the movie.
 * @property genre_ids List of genre IDs associated with the movie.
 * @property id Unique identifier of the movie.
 * @property original_language Original language of the movie.
 * @property original_title Original title of the movie.
 * @property overview Short description or synopsis of the movie.
 * @property popularity Popularity score of the movie.
 * @property poster_path URL path to the poster image of the movie.
 * @property release_date Release date of the movie in ISO 8601 format (YYYY-MM-DD).
 * @property title Title of the movie.
 * @property video Indicates if there is a video associated with the movie.
 * @property vote_average Average vote score of the movie.
 * @property vote_count Total number of votes the movie has received.
 * @property category The category of the movie (e.g., "popular", "top_rated").
 */
data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val category: String,
)