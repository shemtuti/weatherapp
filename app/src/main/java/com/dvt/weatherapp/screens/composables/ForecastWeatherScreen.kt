package com.dvt.weatherapp.screens.composables

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.data.local.model.ForecastWeatherState
import com.dvt.weatherapp.screens.components.ForecastListDetails
import com.dvt.weatherapp.screens.components.ForecastListItems
import com.dvt.weatherapp.utils.Util

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ForecastWeather(
    uiForestState: ForecastWeatherState,
) {
    var showDetails by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var forecastDate by remember { mutableStateOf("") }
    var forecastDay by remember { mutableStateOf("") }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(
            items = uiForestState.forecast,
            itemContent = { forecast ->
                if (forecast.timeForecasted != Util.getCurrentDayOfTheWeek()) {
                    ForecastListItems(forecast = forecast, onItemClick = {
                        forecastDate = forecast.date
                        forecastDay = forecast.day
                        showDetails = true
                    })
                }
            },
        )
    }

    if (showDetails) {
        ModalBottomSheet(
            onDismissRequest = { showDetails = false },
            sheetState = sheetState,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 500.dp),
        ) {
            ForecastListDetails(forecastDate, forecastDay)
        }
    }
}
