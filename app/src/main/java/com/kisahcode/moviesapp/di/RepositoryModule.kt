package com.kisahcode.moviesapp.di

import com.kisahcode.moviesapp.movieList.data.repository.MovieListRepositoryImpl
import com.kisahcode.moviesapp.movieList.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for binding repository implementations to their interfaces.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the MovieListRepositoryImpl implementation to the MovieListRepository interface.
     *
     * @param movieListRepositoryImpl The MovieListRepositoryImpl instance to bind.
     * @return The bound MovieListRepository instance.
     */
    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository

}