package com.example.jpmorganweatherapp.network

import androidx.compose.runtime.snapshots.SnapshotMutableState
import com.example.jpmorganweatherapp.model.City
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("weather?")
    suspend fun getLocation(
        @Query("city name") cityName: String,
        @Query("appid") apiKey: String
    ): Response<SnapshotMutableState<City>>
}