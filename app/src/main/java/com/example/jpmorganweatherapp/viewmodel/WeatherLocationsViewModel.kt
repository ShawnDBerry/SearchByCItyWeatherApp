package com.example.jpmorganweatherapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpmorganweatherapp.model.City
import com.example.jpmorganweatherapp.repository.WeatherLocationsRepository
import com.example.jpmorganweatherapp.util.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import javax.inject.Inject


@HiltViewModel
class WeatherLocationsViewModel @Inject constructor(private val weatherLocationsRepository: WeatherLocationsRepository) :
    ViewModel() {
    val errorMessage = MutableLiveData<String>()
    private lateinit var city: City
    var location = mutableStateOf(city)
    val cityName = mutableStateOf("detroit")
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    init {
        getLocation(cityName.value)
    }

    fun getLocation(cityName: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            weatherLocationsRepository.getLocation(cityName, API_KEY)
                .catch { exception -> onError(exception.message.toString()) }
                .collect { response ->
                    if (response.isSuccess) {
                        withContext(Dispatchers.Main) {
                            Log.e("Q", "locations" + response.getOrNull())
                            response.getOrNull().also {
                                if (it != null) {
                                    location = it
                                }
                            }
                        }
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    private fun onError(message: String) {
        errorMessage.value = message
        Log.e("Error", message)
    }
}

sealed class LocationsUiState {
    data class Success(val location: City) : LocationsUiState()
    data class Error(val exception: Throwable) : LocationsUiState()
}

