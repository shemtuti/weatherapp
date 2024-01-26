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
import com.dvt.weatherapp.data.local.model.FavouriteTable
import com.dvt.weatherapp.screens.components.DrawerButton
import com.dvt.weatherapp.screens.viewmodels.FavouriteViewModel
import com.dvt.weatherapp.screens.viewmodels.WeatherViewModel
import com.dvt.weatherapp.utils.Util
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    onToggleDrawer: () -> Unit,
    viewModel: WeatherViewModel = koinViewModel(),
    viewModelFavourite: FavouriteViewModel = koinViewModel(),
) {
    val uiCurrentState = viewModel.stateCurrentWeather.collectAsState().value
    val uiForestState = viewModel.stateForecastWeather.collectAsState().value

    val weatherCurrent = uiCurrentState.weather?.currentWeather
    val color = Util.getWeatherBackgroundColor(weatherCurrent)

    val currentLocation = uiCurrentState.weather?.locationName

    if (currentLocation.toString().isNotEmpty()) {
        viewModelFavourite.checkIsFavouriteStatus(currentLocation.toString())
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = color),
    ) {
        DrawerButton(onToggleDrawer = onToggleDrawer)

        CurrentWeather(
            uiCurrentState,
            saveFavourite = {
                val favourite = FavouriteTable(
                    dt = uiCurrentState.weather?.dt ?: 0,
                    lat = uiCurrentState.weather?.lat ?: 0.0,
                    lon = uiCurrentState.weather?.lon ?: 0.0,
                    currentWeather = uiCurrentState.weather?.currentWeather ?: "",
                    currentWeatherDesc = uiCurrentState.weather?.currentWeatherDesc ?: "",
                    tempNormal = uiCurrentState.weather?.tempNormal ?: 0.0,
                    tempMax = uiCurrentState.weather?.tempMax ?: 0.0,
                    tempMin = uiCurrentState.weather?.tempMin ?: 0.0,
                    locationName = uiCurrentState.weather?.locationName ?: "",
                )
                viewModelFavourite.saveFavouriteWeather(favourite)
            },
        )

        Spacer(Modifier.size(15.dp))

        Divider(
            color = Color.White,
            thickness = 0.7.dp,
            modifier = Modifier.fillMaxWidth(),
        )

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
                    Util.getTimeOfDataCalculation(uiCurrentState.weather?.dt),
                ),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
            )
        }
    }
}
