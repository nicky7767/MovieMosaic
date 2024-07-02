package com.vinay.moviemosaic.movieScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinay.moviemosaic.data.remote.response.MovieDetails
import com.vinay.moviemosaic.repository.MovieRepository
import com.vinay.moviemosaic.util.Constants
import com.vinay.moviemosaic.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _moviesList = MutableStateFlow<List<Resource<MovieDetails>>>(emptyList())
    val moviesList: StateFlow<List<Resource<MovieDetails>>> = _moviesList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _endReached = MutableStateFlow(false)
    val endReached: StateFlow<Boolean> = _endReached

    private var currentBatchStartIndex = 0
    private var currPage = 1

    init {
        fetchMoviesPaginated()
    }

    fun fetchMoviesPaginated() {
        viewModelScope.launch {
            if (_isLoading.value) return@launch
            _isLoading.value = true
            val newMoviesList = movieRepository.getMovieDetailsBatch(
                limit = Constants.PAGE_SIZE,
                offset = currentBatchStartIndex
            )
            _endReached.value = currPage * Constants.PAGE_SIZE >= Constants.imdbIds.size
            _moviesList.value += newMoviesList
            currentBatchStartIndex += Constants.PAGE_SIZE
            _isLoading.value = false
            currPage++
        }
    }
}