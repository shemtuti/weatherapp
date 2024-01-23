package com.dvt.weatherapp.screens.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.R
import com.dvt.weatherapp.data.local.model.CurrentWeatherState
import com.dvt.weatherapp.screens.components.DaysTemp
import com.dvt.weatherapp.utils.Util
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import java.util.Locale

@Composable
fun CurrentWeather(uiCurrentState: CurrentWeatherState) {
    val context = LocalContext.current
    val weatherMain = uiCurrentState.weather?.currentWeather

    Box(
        Modifier
            .height(350.dp)
            .paint(
                painter = rememberDrawablePainter(
                    drawable = Util.getWeatherBackgroundDrawable(
                        context = context,
                        weatherMain
                    )
                ),
                contentScale = ContentScale.FillBounds
            )
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth()
                .padding(16.dp)
                .wrapContentHeight()

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = rememberDrawablePainter(
                        drawable = Util.getLocationIconDrawable(
                            context = context,
                            weatherMain
                        )
                    ),
                    contentDescription = stringResource(R.string.location_icon),
                )
                Text(
                    text = uiCurrentState.weather?.locationName.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = String.format(
                    Util.getFormatTemp(uiCurrentState.weather?.tempNormal) + stringResource(R.string.temp_symbol)
                ),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = uiCurrentState.weather?.currentWeatherDesc.toString()
                    .uppercase(Locale.getDefault()),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, top = 32.dp, end = 18.dp)
        ) {
            DaysTemp(uiCurrentState.weather?.tempMin, "min")
            DaysTemp(uiCurrentState.weather?.tempNormal, "Current")
            DaysTemp(uiCurrentState.weather?.tempMax, "max")
        }
    }

}