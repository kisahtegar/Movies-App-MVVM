package com.kisahcode.moviesapp.movieList.domain.repository

import com.kisahcode.moviesapp.movieList.domain.model.Movie
import com.kisahcode.moviesapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * A repository interface providing access to movie-related data.
 */
interface MovieListRepository {

    /**
     * Retrieves a list of movies based on the specified category and page.
     *
     * @param forceFetchFromRemote Whether to force fetch data from the remote data source.
     * @param category The category of movies to retrieve (e.g., "popular", "upcoming").
     * @param page The page number of the movie list to retrieve.
     * @return A Flow emitting [Resource]s containing the list of movies.
     */
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    /**
     * Retrieves detailed information about a specific movie.
     *
     * @param id The ID of the movie to retrieve.
     * @return A Flow emitting [Resource]s containing the movie details.
     */
    suspend fun getMovie(id: Int): Flow<Resource<Movie>>

}