package com.mahmutalperenunal.tmdbmovieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmutalperenunal.tmdbmovieapp.model.MovieItem
import com.mahmutalperenunal.tmdbmovieapp.network.ApiClient
import com.mahmutalperenunal.tmdbmovieapp.util.Constants
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {

    val movieList: MutableLiveData<List<MovieItem?>?> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val errorMessage: MutableLiveData<String?> = MutableLiveData()

    // MutableLiveData holding selected category information
    private val selectedCategory = MutableLiveData<String>()

    // MutableLiveData holding a list of movies filtered by the selected category
    private val _filteredMovieList: MutableLiveData<List<MovieItem?>?> = MutableLiveData()
    val filteredMovieList: LiveData<List<MovieItem?>?> get() = _filteredMovieList

    init {
        getMovieList()
    }

    private fun getMovieList() {
        isLoading.value = true

        viewModelScope.launch {
            try {

                // Make an API request using the Retrofit client according to the selected category
                val response = when (selectedCategory.value ?: "Popular") {
                    "Popular" -> ApiClient.getClient()
                        .getPopularMovieList(token = Constants.BEARER_TOKEN)

                    "Top Rated" -> ApiClient.getClient()
                        .getTopRatedMovieList(token = Constants.BEARER_TOKEN)

                    "Upcoming" -> ApiClient.getClient()
                        .getUpcomingMovieList(token = Constants.BEARER_TOKEN)

                    "Now Playing" -> ApiClient.getClient()
                        .getNowPlayingMovieList(token = Constants.BEARER_TOKEN)

                    else -> ApiClient.getClient()
                        .getPopularMovieList(token = Constants.BEARER_TOKEN)
                }

                if (response.isSuccessful) {
                    movieList.postValue(response.body()?.movieItems)
                } else {
                    if (response.message().isNullOrEmpty()) {
                        errorMessage.value = "An unknown error occurred"
                    } else {
                        errorMessage.value = response.message()
                    }
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

    fun onCategorySelected(category: String) {
        // Update the selected category information and retrieve the movie list
        selectedCategory.value = category
        getMovieList()
    }

    fun filterMovies(query: String) {
        val fullMovieList = movieList.value
        if (fullMovieList != null) {
            // Filter by search query and post to _filteredMovieList LiveData
            val filteredList = fullMovieList.filter {
                it?.title?.contains(query, ignoreCase = true) == true
            }
            _filteredMovieList.value = filteredList
        }
    }

}