package com.vinay.moviemosaic.di

import com.vinay.moviemosaic.data.remote.api.MovieApi
import com.vinay.moviemosaic.repository.MovieRepository
import com.vinay.moviemosaic.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieApi: MovieApi
    ) = MovieRepository(movieApi)

    @Singleton @Provides
    fun provideMovieApi(): MovieApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(MovieApi::class.java)
    }

}