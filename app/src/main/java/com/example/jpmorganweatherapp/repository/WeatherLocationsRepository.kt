package com.example.jpmorganweatherapp.repository

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.jpmorganweatherapp.model.City
import com.example.jpmorganweatherapp.network.OpenWeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class WeatherLocationsRepository @Inject constructor(private val itemsService: OpenWeatherService) {


   suspend fun getLocation(cityName: String, aaid: String): Flow<Result<SnapshotMutableState<City>?>> {
       return createFlow(cityName, aaid)
    }

    private suspend fun createFlow(cityName: String, aaid: String): Flow<Result<SnapshotMutableState<City>?>> {
        return flow {
            val result = itemsService.getLocation(cityName, aaid)
            if(result.isSuccessful){
                Log.e("flow", result.toString())
                emit(Result.success(result.body()))
            }
        }.flowOn(Dispatchers.IO)
    }

}