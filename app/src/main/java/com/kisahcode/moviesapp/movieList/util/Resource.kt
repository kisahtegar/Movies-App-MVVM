package com.kisahcode.moviesapp.movieList.util

/**
 * A sealed class representing a generic resource that encapsulates data, loading status, or error information.
 * This class is used to handle the states of data loading in the app.
 *
 * @param T The type of data encapsulated by the resource.
 * @property data The encapsulated data. Can be `null` if the resource represents an error or loading state.
 * @property message A message describing the error or any additional information related to the resource.
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {

    /**
     * Represents a successful state where data is loaded successfully.
     *
     * @param data The loaded data.
     */
    class Success<T>(data: T?) : Resource<T>(data)

    /**
     * Represents an error state where data loading failed.
     *
     * @param message A message describing the error.
     * @param data The data associated with the error, if any.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    /**
     * Represents a loading state where data is being loaded or refreshed.
     *
     * @param isLoading Indicates whether the app is in a loading state. Default is `true`.
     */
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null)

}