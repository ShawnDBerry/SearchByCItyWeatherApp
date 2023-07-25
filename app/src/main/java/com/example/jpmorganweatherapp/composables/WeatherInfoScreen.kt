package com.example.jpmorganweatherapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jpmorganweatherapp.model.*

@Composable
fun WeatherInfoScreen(weatherInfo: City) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "City: ${weatherInfo.name}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Temperature: ${weatherInfo.main.temp}°C")

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = rememberAsyncImagePainter(
                    "http://openweathermap.org/img/w/${weatherInfo.weather[0].icon}.png"
                ),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val weatherList = mutableListOf<Weather>()
    val weather = Weather(12,"", "", "")
    weatherList.add(weather)
    val city = City(
        Coord(12.23, 13.0), weatherList, "", Main(12.1, 12.2, 78.3, 98.3, 34, 323), 1321,
        Wind(3234, 3223), Clouds(21), 34, Sys(234, 12, "US", 234, 65), 323, 12, "birmingham", 23
    )
    WeatherInfoScreen(city)
}