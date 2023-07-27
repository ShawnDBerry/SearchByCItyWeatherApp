package com.example.jpmorganweatherapp.repository

import com.example.jpmorganweatherapp.model.City
import com.example.jpmorganweatherapp.network.OpenWeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class WeatherLocationsRepository @Inject constructor(private val itemsService: OpenWeatherService) {


   suspend fun getLocation(cityName: String, aaid: String): Flow<Result<City?>> {
       return createFlow(cityName, aaid)
    }

    private suspend fun createFlow(cityName: String, aaid: String): Flow<Result<City?>> {
        return flow {
            val result = itemsService.getLocation(cityName, aaid)
            if(result.isSuccessful){
                emit(Result.success(result.body()))
            }
        }.flowOn(Dispatchers.IO)
    }

}