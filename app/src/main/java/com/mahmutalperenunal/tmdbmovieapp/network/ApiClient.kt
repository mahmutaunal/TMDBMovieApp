package com.mahmutalperenunal.tmdbmovieapp.network

import com.mahmutalperenunal.tmdbmovieapp.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// A Kotlin object that creates the API client using Retrofit and OkHttpClient
object ApiClient {

    // Function that returns API service
    fun getClient(): ApiService {
        // Creating HttpLoggingInterceptor to log HTTP requests and responses
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // Creating OkHttpClient
        val client: OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS) // connection time limit
                .readTimeout(60, TimeUnit.SECONDS) // reading time limit
                .build()

        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            .create(ApiService::class.java)
    }
}