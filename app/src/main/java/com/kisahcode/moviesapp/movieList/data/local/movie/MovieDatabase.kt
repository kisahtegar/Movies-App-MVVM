package com.kisahcode.moviesapp.movieList.data.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The main database class for the application, which holds the database configuration and serves as
 * the main access point to the persisted data. This class uses Room's `@Database` annotation to
 * define the list of entities and the database version.
 *
 * @property movieDao Provides access to the movie-related database operations defined in the [MovieDao] interface.
 */
@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    /**
     * Abstract method to get an instance of [MovieDao].
     * This method provides the DAO for accessing movie data.
     */
    abstract val movieDao: MovieDao
}