package com.kisahcode.moviesapp.movieList.data.local.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing a movie in the local database.
 *
 * This class defines the schema for the `MovieEntity` table in Room.
 *
 * @property id The unique identifier for the movie. This serves as the primary key.
 * @property adult Indicates if the movie is for adults only.
 * @property backdrop_path URL path to the backdrop image of the movie.
 * @property genre_ids Comma-separated string of genre IDs associated with the movie.
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
@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Int,

    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: String,
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