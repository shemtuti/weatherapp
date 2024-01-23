package com.dvt.weatherapp.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.R
import com.dvt.weatherapp.data.local.model.ForecastDayWeatherState
import com.dvt.weatherapp.screens.viewmodels.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ForecastListDetails(
    forecastDate: String,
    forecastDay: String,
    viewModel: WeatherViewModel = koinViewModel()
) {
    var uiForecastDetailsState by remember { mutableStateOf<List<ForecastDayWeatherState>?>(null) }

    LaunchedEffect(forecastDate) {
        viewModel.getForecastDayWeather(forecastDate)
            .collect { result ->
                uiForecastDetailsState = result
            }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Text(
            text = "Weather Forecast for $forecastDay",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Box(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(end = 14.dp)
            .padding(start = 14.dp)
            .padding(top = 8.dp)
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.time),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = stringResource(R.string.weather),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = stringResource(R.string.empty),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = stringResource(R.string.min_temp),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = stringResource(R.string.max_temp),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }

    uiForecastDetailsState?.let { state ->
        FlowColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            state.forEach { items ->
                DetailsItem(items)
            }
        }
    }

    Spacer(modifier = Modifier.height(10.dp))


}
