package com.dvt.weatherapp.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.R
import com.dvt.weatherapp.data.local.model.ForecastTable
import com.dvt.weatherapp.utils.Util
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun ForecastListItems(
    forecast: ForecastTable,
    onItemClick: () -> Unit,
) {
    val context = LocalContext.current
    Box(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(end = 14.dp)
            .padding(start = 14.dp)
            .padding(top = 8.dp)
            .clickable {
                onItemClick()
            },
    ) {
        Text(
            text = String.format(Util.getDayOfWeekFromUTC(forecast.dt)),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterStart),
        )

        Image(
            modifier = Modifier.align(Alignment.Center).size(20.dp),
            painter = rememberDrawablePainter(
                drawable = Util.getWeatherIconDrawable(
                    context = context,
                    forecast.currentWeather,
                ),
            ),
            contentDescription = stringResource(R.string.weather_icon),
        )
        Text(
            text = String.format(
                Util.getFormatTemp(forecast.tempNormal) + stringResource(R.string.temp_symbol),
            ),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterEnd),
        )
    }
}
