package com.kisahcode.moviesapp.movieList.data.remote

import com.kisahcode.moviesapp.BuildConfig
import com.kisahcode.moviesapp.movieList.data.remote.response.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface for defining the Movie API endpoints and their respective request methods.
 * This interface uses Retrofit annotations to define how HTTP requests are made.
 */
interface MovieApi {

    /**
     * Fetches a list of movies based on the specified category and page number.
     *
     * @param category The category of movies to fetch (e.g., "popular", "top_rated").
     * @param page The page number to retrieve (for pagination).
     * @param apiKey The API key for authenticating the request. Defaults to [API_KEY_TMDB].
     * @return A [MovieListDto] object containing the list of movies and additional metadata.
     */
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY_TMDB
    ): MovieListDto

    companion object {
        /**
         * The base URL for accessing the Movie Database API.
         */
        const val BASE_URL = "https://api.themoviedb.org/3/"

        /**
         * The base URL for accessing movie poster images.
         */
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

        /**
         * The API key for authenticating requests to the Movie Database API.
         * This key is typically stored in the build configuration for security reasons.
         */
        const val API_KEY_TMDB = BuildConfig.API_KEY_TMDB
    }

}