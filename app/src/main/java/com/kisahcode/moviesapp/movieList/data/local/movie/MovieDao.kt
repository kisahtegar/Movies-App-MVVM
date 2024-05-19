package com.kisahcode.moviesapp.movieList.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/**
 * Data Access Object (DAO) for accessing and managing movie data in the local database.
 * This interface defines methods for performing CRUD operations on the `MovieEntity` table.
 */
@Dao
interface MovieDao {

    /**
     * Inserts or updates a list of movies in the local database.
     *
     * If a movie already exists, it will be updated. Otherwise, it will be inserted.
     *
     * @param movieList The list of [MovieEntity] objects to be upserted.
     */
    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    /**
     * Retrieves a movie from the local database by its unique identifier.
     *
     * @param id The unique identifier of the movie to retrieve.
     * @return The [MovieEntity] object representing the movie with the specified ID.
     */
    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity

    /**
     * Retrieves a list of movies from the local database that belong to a specific category.
     *
     * @param category The category of movies to retrieve.
     * @return A list of [MovieEntity] objects representing the movies in the specified category.
     */
    @Query("SELECT * FROM MovieEntity WHERE category = :category")
    suspend fun getMovieListByCategory(category: String): List<MovieEntity>
}