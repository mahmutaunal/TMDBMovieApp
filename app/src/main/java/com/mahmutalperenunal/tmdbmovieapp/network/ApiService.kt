package com.mahmutalperenunal.tmdbmovieapp.network

import com.mahmutalperenunal.tmdbmovieapp.model.MovieDetailResponse
import com.mahmutalperenunal.tmdbmovieapp.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovieList(
        @Header("Authorization") token: String
    ): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovieList(
        @Header("Authorization") token: String
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieList(
        @Header("Authorization") token: String
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovieList(
        @Header("Authorization") token: String
    ): Response<MovieResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: String,
        @Header("Authorization") token: String
    ): Response<MovieDetailResponse>

}