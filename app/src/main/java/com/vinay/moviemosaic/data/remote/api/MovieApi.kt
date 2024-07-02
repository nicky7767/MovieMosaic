package com.vinay.moviemosaic.data.remote.api

import com.vinay.moviemosaic.data.remote.response.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET(".")
    suspend fun getMovieDetails(
        @Query("i") i: String,
        @Query("apikey") apikey: String
    ): MovieDetails
}