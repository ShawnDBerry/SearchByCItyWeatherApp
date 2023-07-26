package com.example.jpmorganweatherapp.composables

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.rememberAsyncImagePainter
import com.example.jpmorganweatherapp.model.*

@Composable
fun WeatherInfoScreen(
    city: City, cityName: String,
    searchCity: (String)->Unit) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var text by rememberSaveable { mutableStateOf(cityName) }
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                label = { Text("Search By City") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            val context = LocalContext.current
            Button(
                onClick = {
                    searchCity(text)
                    Toast.makeText(
                        context, "This is my Toast message!",
                        Toast.LENGTH_LONG
                    ).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Search")
            }
            Text(text = "City: ${city.name}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Temperature: ${city.main?.temp}°C")

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = rememberAsyncImagePainter(
                    "http://openweathermap.org/img/w/${city.weather?.get(0)?.icon}.png"
                ),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val weatherList = mutableListOf<Weather>()
    val weather = Weather(12,"", "", "")
    weatherList.add(weather)
    val city = City(
        Coord(12.23, 13.0), weatherList, "", Main(12.1, 12.2, 78.3, 98.3, 34, 323), 1321,
        Wind(3234, 3223), Clouds(21), 34, Sys(234, 12, "US", 234, 65), 323, 12, "birmingham", 23
    )
    WeatherInfoScreen(city,"birmingham"){}
}