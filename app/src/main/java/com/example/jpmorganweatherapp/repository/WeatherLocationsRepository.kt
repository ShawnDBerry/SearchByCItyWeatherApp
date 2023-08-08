package com.example.jpmorganweatherapp.repository

import com.example.jpmorganweatherapp.model.City
import com.example.jpmorganweatherapp.network.OpenWeatherService
import com.example.jpmorganweatherapp.util.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherLocationsRepository @Inject constructor(private val itemsService: OpenWeatherService) {

   suspend fun getLocation(cityName: String): Flow<Result<City?>> {
       return createFlow(cityName)
    }

    private suspend fun createFlow(cityName: String): Flow<Result<City?>> {
        return flow {
            val result = itemsService.getLocation(cityName, API_KEY)
            if(result.isSuccessful){
                emit(Result.success(result.body()))
            }
        }.flowOn(Dispatchers.IO)
    }
}