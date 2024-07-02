package com.vinay.moviemosaic.repository

import com.vinay.moviemosaic.data.remote.api.MovieApi
import com.vinay.moviemosaic.data.remote.response.MovieDetails
import com.vinay.moviemosaic.util.Constants
import com.vinay.moviemosaic.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import kotlin.math.min


@ActivityScoped
class MovieRepository @Inject constructor(private val movieApi: MovieApi) {
    suspend fun getMovieDetails(i: String, apikey: String): Resource<MovieDetails> {
        val response = try {
                movieApi.getMovieDetails(i, apikey)
            } catch (e: Exception) {
                println(e)
                return Resource.Error(e.message ?: "An unknown error occurred.")
            }
        return Resource.Success(response)
    }

    suspend fun getMovieDetailsBatch(limit: Int, offset: Int): List<Resource<MovieDetails>> {
        val moviesCount = Constants.imdbIds.size
        val endInd = min(offset + limit, moviesCount - 1)
        return Constants.imdbIds.subList(offset, endInd).map {
            id ->  getMovieDetails(i = id, apikey = Constants.API_KEY)
        }
    }
}