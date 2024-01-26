package com.dvt.weatherapp.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.R
import com.dvt.weatherapp.data.local.model.ForecastDayWeatherState
import com.dvt.weatherapp.utils.Util
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsItem(
    item: ForecastDayWeatherState,
) {
    val context = LocalContext.current
    Box(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(end = 14.dp)
            .padding(start = 14.dp)
            .padding(top = 5.dp),
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = item.time.toString(),
                style = MaterialTheme.typography.displayMedium,
            )

            Text(
                text = item.currentWeather.toString(),
                style = MaterialTheme.typography.displayMedium,
            )

            Image(
                modifier = Modifier.size(20.dp),
                painter = rememberDrawablePainter(
                    drawable = Util.getWeatherIconDrawable(
                        context = context,
                        item.currentWeather,
                    ),
                ),
                contentDescription = stringResource(R.string.weather_icon),
            )

            Text(
                text = Util.getFormatTemp(item.tempMin) + stringResource(R.string.temp_symbol),
                style = MaterialTheme.typography.displayMedium,
            )

            Text(
                text = String.format(
                    Util.getFormatTemp(item.tempMax) + stringResource(R.string.temp_symbol),
                ),
                style = MaterialTheme.typography.displayMedium,
            )
        }
    }
}
