package com.example.jpmcopenweatherapp.composables

import android.app.PendingIntent.getActivity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(
    cityName: String?,
//    onCityNameChange: (String) -> Unit,
//    onSearchClicked: () -> Unit,
) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = cityName?: "")
//            BasicTextField(
//                value = cityName ?: "",
//                //onValueChange = { onCityNameChange(it) },
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
//                //keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
//                singleLine = true,
//                textStyle = LocalTextStyle.current.copy(color = Color.Black)
//            )

            Spacer(modifier = Modifier.height(16.dp))
            val context = LocalContext.current
            Button(
                onClick = { Toast.makeText(context, "This is my Toast message!",
                    Toast.LENGTH_LONG).show(); },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Search")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SearchScreen("birmingham")
}

//@Composable
//fun WeatherApp(weatherLocationsViewModel: WeatherLocationsViewModel) {
//
//}
