package com.kisahcode.moviesapp.movieList.data.mappers

import com.kisahcode.moviesapp.movieList.data.local.movie.MovieEntity
import com.kisahcode.moviesapp.movieList.data.remote.response.MovieDto
import com.kisahcode.moviesapp.movieList.domain.model.Movie

/**
 * Extension function to convert a MovieDto to a MovieEntity.
 *
 * @param category The category of the movie.
 * @return The converted MovieEntity object.
 */
fun MovieDto.toMovieEntity(
    category: String
): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        backdrop_path = backdrop_path ?: "",
        original_language = original_language ?: "",
        overview = overview ?: "",
        poster_path = poster_path ?: "",
        release_date = release_date ?: "",
        title = title ?: "",
        vote_average = vote_average ?: 0.0,
        popularity = popularity ?: 0.0,
        vote_count = vote_count ?: 0,
        id = id ?: -1,
        original_title = original_title ?: "",
        video = video ?: false,
        category = category,

        // Represents the genre IDs associated with the movie.
        // The IDs are stored as a comma-separated string.
        // If the genre IDs are not available, default values are used.
        genre_ids = try {
            genre_ids?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        }
    )
}

/**
 * Extension function to convert a MovieEntity to a Movie.
 *
 * @param category The category of the movie.
 * @return The converted Movie object.
 */
fun MovieEntity.toMovie(
    category: String
): Movie {
    return Movie(
        backdrop_path = backdrop_path,
        original_language = original_language,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        popularity = popularity,
        vote_count = vote_count,
        video = video,
        id = id,
        adult = adult,
        original_title = original_title,
        category = category,

        // Represents the genre IDs associated with the movie.
        // The IDs are stored as a comma-separated string in the MovieEntity
        // and are converted back to a list of integers for the Movie object.
        // If the conversion fails, default values are used for genre IDs.
        genre_ids = try {
            genre_ids.split(",").map { it.toInt() }
        } catch (e: Exception) {
            listOf(-1, -2)
        }
    )
}