package com.kisahcode.moviesapp.movieList.data.repository

import com.kisahcode.moviesapp.movieList.data.local.movie.MovieDatabase
import com.kisahcode.moviesapp.movieList.data.mappers.toMovie
import com.kisahcode.moviesapp.movieList.data.mappers.toMovieEntity
import com.kisahcode.moviesapp.movieList.data.remote.MovieApi
import com.kisahcode.moviesapp.movieList.domain.model.Movie
import com.kisahcode.moviesapp.movieList.domain.repository.MovieListRepository
import com.kisahcode.moviesapp.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


/**
 * Implementation of the [MovieListRepository] interface.
 *
 * Handles the retrieval of movie data from both local and remote sources.
 */
class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {

    /**
     * Retrieves a list of movies based on the specified category and page.
     *
     * The function first attempts to fetch the data from the local database. If the data is not
     * available locally or a remote fetch is forced, it retrieves the data from the remote API
     * and updates the local database.
     *
     * @param forceFetchFromRemote Whether to force fetch data from the remote data source.
     * @param category The category of movies to retrieve (e.g., "popular", "upcoming").
     * @param page The page number of the movie list to retrieve.
     * @return A Flow emitting [Resource]s containing the list of movies.
     */
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            // Retrieve movie list from local database based on category
            val localMovieList = movieDatabase.movieDao.getMovieListByCategory(category)

            // Check if local movie list is available and no remote fetch is forced
            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie) {
                // Emit local movie list as success resource
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            // Fetch movie list from remote API
            val movieListFromApi = try {
                movieApi.getMoviesList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            // Convert movie DTOs to entities
            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            // Update local database with fetched movie entities
            movieDatabase.movieDao.upsertMovieList(movieEntities)

            // Emit fetched movie list as success resource
            emit(Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))
        }
    }

    /**
     * Retrieves detailed information about a specific movie from the local database.
     *
     * The function retrieves a movie entity from the local database using the provided movie ID.
     * If the movie entity is found, it is converted to a [Movie] domain model and emitted as a
     * [Resource.Success]. If the movie entity is not found, an [Resource.Error] is emitted with an
     * appropriate error message.
     *
     * @param id The ID of the movie to retrieve.
     * @return A Flow emitting [Resource]s containing the movie details.
     */
    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            // Retrieve movie entity from the local database
            val movieEntity = movieDatabase.movieDao.getMovieById(id)

            // Check if the movie entity exists
            if (movieEntity != null) {
                // Convert the movie entity to a Movie domain model and emit as a success resource
                emit(Resource.Success(movieEntity.toMovie(movieEntity.category)))

                emit(Resource.Loading(false))
                return@flow
            }

            // Emit an error resource if the movie entity does not exist
            emit(Resource.Error("Error no such movie"))
            emit(Resource.Loading(false))
        }
    }

}