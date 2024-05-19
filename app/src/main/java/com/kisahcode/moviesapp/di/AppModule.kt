package com.kisahcode.moviesapp.di

import android.app.Application
import androidx.room.Room
import com.kisahcode.moviesapp.movieList.data.local.movie.MovieDatabase
import com.kisahcode.moviesapp.movieList.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing application-level dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Create an interceptor for logging HTTP requests and responses
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Create an OkHttpClient with the logging interceptor
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    /**
     * Provides a singleton instance of the MovieApi interface.
     *
     * @return The singleton MovieApi instance.
     */
    @Provides
    @Singleton
    fun providesMovieApi() : MovieApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .build()
            .create(MovieApi::class.java)
    }

    /**
     * Provides a singleton instance of the MovieDatabase.
     *
     * @param app The application context.
     * @return The singleton MovieDatabase instance.
     */
    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "moviedb.db"
        ).build()
    }

}