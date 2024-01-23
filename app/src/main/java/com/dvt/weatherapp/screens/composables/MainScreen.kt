package com.dvt.weatherapp.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.screens.viewmodels.WeatherViewModel
import com.dvt.weatherapp.utils.Util
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: WeatherViewModel = koinViewModel()
) {
    val uiCurrentState = viewModel.stateCurrentWeather.collectAsState().value
    val uiForestState = viewModel.stateForecastWeather.collectAsState().value

    val weatherCurrent = uiCurrentState.weather?.currentWeather
    val color = Util.getWeatherBackgroundColor(weatherCurrent)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = color)
    ) {
        CurrentWeather(uiCurrentState)

        Spacer(Modifier.size(15.dp))

        Divider(
            color = Color.White,
            thickness = 0.7.dp,
            modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.size(15.dp))

        ForecastWeather(uiForestState)

        Spacer(modifier = Modifier.weight(1f))

        if (uiCurrentState.weather?.dt != null) {
            Text(
                modifier = Modifier
                    .padding(bottom = 5.dp, end = 5.dp)
                    .align(Alignment.End),
                text = String.format(
                    "Last checked %s",
                    Util.getTimeOfDataCalculation(uiCurrentState.weather?.dt)
                ),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}