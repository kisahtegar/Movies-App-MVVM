package com.kisahcode.moviesapp.movieList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisahcode.moviesapp.movieList.domain.repository.MovieListRepository
import com.kisahcode.moviesapp.movieList.util.Category
import com.kisahcode.moviesapp.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and business logic of the movie list screen.
 *
 * It interacts with the [MovieListRepository] to fetch movie data and exposes the current state
 * of the movie list screen to the UI layer.
 *
 * @param movieListRepository The repository responsible for fetching movie data.
 */
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    // Represents the current state of the movie list screen.
    private var _movieListState = MutableStateFlow(MovieListState())
    // Represents the immutable state flow of the movie list screen.
    val movieListState = _movieListState.asStateFlow()

    /**
     * Initializes the movie list ViewModel by fetching the popular and upcoming movie lists.
     * By default, it fetches data from the local cache without forcing a remote fetch.
     */
    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    /**
     * Handles incoming UI events triggered by user actions on the Movie List screen.
     * Events can include navigation requests or pagination requests for movie lists.
     *
     * @param event The UI event to handle.
     */
    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            // Navigate event, Toggles between displaying popular and upcoming movie lists.
            MovieListUiEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }

            // Paginate event, Requests additional movie list items based on the specified category.
            is MovieListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    // Request to paginate the popular movie list.
                    getPopularMovieList(true)
                } else if (event.category == Category.UPCOMING) {
                    // Request to paginate the upcoming movie list.
                    getUpcomingMovieList(true)
                }
            }
        }
    }

    /**
     * Retrieves the popular movie list from the repository and updates the UI state accordingly.
     *
     * If [forceFetchFromRemote] is true, the function fetches data from the remote data source
     * otherwise, it retrieves data from the local cache if available.
     *
     * @param forceFetchFromRemote Boolean flag indicating whether to force fetching data from the remote source.
     */
    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            // Update UI state to indicate loading.
            _movieListState.update {
                it.copy(isLoading = true)
            }

            // Fetch popular movie list from the repository.
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                // Handle the result received from the repository.
                when (result) {
                    is Resource.Error -> {
                        // Update UI state in case of an error.
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        // Update UI state with the retrieved popular movie list.
                        result.data?.let { popularList ->
                            _movieListState.update {
                                it.copy(
                                    popularMovieList = movieListState.value.popularMovieList
                                            + popularList.shuffled(), // Shuffling the list for better user experience.
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        // Update UI state based on the loading status.
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

    /**
     * Retrieves the upcoming movie list from the repository and updates the UI state accordingly.
     *
     * If [forceFetchFromRemote] is true, the function fetches data from the remote data source
     * otherwise, it retrieves data from the local cache if available.
     *
     * @param forceFetchFromRemote Boolean flag indicating whether to force fetching data from the remote source.
     */
    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        // Update UI state to indicate loading.
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }

            // Fetch upcoming movie list from the repository.
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.UPCOMING,
                movieListState.value.upcomingMovieListPage
            ).collectLatest { result ->
                // Handle the result received from the repository.
                when (result) {
                    is Resource.Error -> {
                        // Update UI state in case of an error.
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> {
                        // Update UI state with the retrieved upcoming movie list.
                        result.data?.let { upcomingList ->
                            _movieListState.update {
                                it.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList
                                            + upcomingList.shuffled(), // Shuffling the list for better user experience.
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        // Update UI state based on the loading status.
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

}