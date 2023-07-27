package com.example.jpmorganweatherapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpmorganweatherapp.model.City
import com.example.jpmorganweatherapp.repository.WeatherLocationsRepository
import com.example.jpmorganweatherapp.util.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class WeatherLocationsViewModel @Inject constructor(private val weatherLocationsRepository: WeatherLocationsRepository) :
    ViewModel() {
    private val errorMessage = MutableLiveData<String>()
    private var city =
        City(null, null, null, null, null, null, null, null, null, null, null, null, null)
    var location = mutableStateOf(city)
    var cityName = mutableStateOf("")
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    init {
        getLocation(cityName.value)
    }

    fun getLocation(cityName: String) {
        viewModelScope.launch(exceptionHandler) {
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