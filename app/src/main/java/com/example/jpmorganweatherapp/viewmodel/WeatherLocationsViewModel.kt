package com.example.jpmorganweatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
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
    private val _city = MutableLiveData<City>()
    val city: LiveData<City> get() = _city
    private val _cityName = MutableLiveData("")
    val cityName: LiveData<String> get() = _cityName
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    init {
        getLocation(_cityName.value)
    }

    fun getLocation(cityName: String?) {
        viewModelScope.launch(exceptionHandler) {
            if (cityName != null) {
                weatherLocationsRepository.getLocation(cityName, API_KEY)
                    .catch { exception -> onError(exception.message.toString()) }
                    .collect { response ->
                        if (response.isSuccess) {
                            withContext(Dispatchers.Main) {
                                response.getOrNull().also {
                                    if (it != null) {
                                        _city.value = it
                                        Log.e("Q", "city" + city.value)
                                    }
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