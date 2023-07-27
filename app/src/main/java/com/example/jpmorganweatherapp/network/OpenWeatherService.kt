package com.example.jpmorganweatherapp.network

import com.example.jpmorganweatherapp.model.City
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("weather?")
    suspend fun getLocation(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Response<City>
}