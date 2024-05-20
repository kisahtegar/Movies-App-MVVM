package com.kisahcode.moviesapp.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisahcode.moviesapp.movieList.domain.repository.MovieListRepository
import com.kisahcode.moviesapp.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and business logic of the movie details screen.
 *
 * @property movieListRepository The repository to fetch movie details from.
 * @property savedStateHandle Handle to saved state allowing access to saved state information and movie ID.
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // The ID of the movie to fetch details for, retrieved from saved state handle
    private val movieId = savedStateHandle.get<Int>("movieId")

    // MutableStateFlow to hold and update the details state
    private var _detailsState = MutableStateFlow(DetailsState())

    // StateFlow to expose the details state as an immutable flow
    val detailsState = _detailsState.asStateFlow()

    // Initialization block to fetch movie details when the ViewModel is created
    init {
        getMovie(movieId ?: -1)
    }

    /**
     * Fetches the details of a movie by its ID.
     *
     * This function launches a coroutine to fetch movie details and update the state accordingly.
     *
     * @param id The ID of the movie to fetch details for.
     */
    private fun getMovie(id: Int) {
        viewModelScope.launch {
            // Update the state to show loading
            _detailsState.update {
                it.copy(isLoading = true)
            }

            // Collect the result from the repository and update the state based on the result
            movieListRepository.getMovie(id).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        // Update the state to show that loading has stopped
                        _detailsState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        // Update the loading state
                        _detailsState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        // Update the state with the fetched movie details
                        result.data?.let { movie ->
                            _detailsState.update {
                                it.copy(movie = movie)
                            }
                        }
                    }
                }
            }
        }
    }
}