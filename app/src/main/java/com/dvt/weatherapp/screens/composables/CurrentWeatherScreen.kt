package com.dvt.weatherapp.screens.composables

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.R
import com.dvt.weatherapp.data.local.model.CurrentWeatherState
import com.dvt.weatherapp.screens.components.DaysTemp
import com.dvt.weatherapp.screens.viewmodels.FavouriteViewModel
import com.dvt.weatherapp.utils.Util
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import java.util.Locale
import org.koin.androidx.compose.koinViewModel

@Composable
fun CurrentWeather(
    uiCurrentState: CurrentWeatherState,
    saveFavourite: () -> Unit,
    viewModel: FavouriteViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val weatherMain = uiCurrentState.weather?.currentWeather
    var isFavourite by remember { mutableStateOf(false) }

    val isFavouriteState = viewModel.isLocationNameExists.collectAsState().value

    Box(
        Modifier
            .height(350.dp)
            .paint(
                painter = rememberDrawablePainter(
                    drawable = Util.getWeatherBackgroundDrawable(
                        context = context,
                        weatherMain,
                    ),
                ),
                contentScale = ContentScale.FillBounds,
            )
            .fillMaxWidth(),
    ) {
        IconButton(
            onClick = {
                if (isFavourite) {
                    viewModel.deleteFavouriteByName(uiCurrentState.weather?.locationName.toString())
                    Toast.makeText(context, "Deleted from favourites", Toast.LENGTH_SHORT).show()
                } else {
                    saveFavourite()
                    Toast.makeText(context, "Saved to favourites", Toast.LENGTH_SHORT).show()
                }
                isFavourite = !isFavourite
            },
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopEnd)
                .padding(top = 10.dp, end = 10.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_favourite),
                contentDescription = "",
                tint = if (isFavourite) Color.Red else Color.Unspecified,
                modifier = Modifier
                    .size(30.dp),
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth()
                .padding(16.dp)
                .wrapContentHeight(),

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = Modifier.size(22.dp),
                    painter = rememberDrawablePainter(
                        drawable = Util.getLocationIconDrawable(
                            context = context,
                            weatherMain,
                        ),
                    ),
                    contentDescription = stringResource(R.string.location_icon),
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = uiCurrentState.weather?.locationName.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = String.format(
                    Util.getFormatTemp(uiCurrentState.weather?.tempNormal) + stringResource(R.string.temp_symbol),
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
                .padding(start = 18.dp, top = 32.dp, end = 18.dp),
        ) {
            DaysTemp(uiCurrentState.weather?.tempMin, "min")
            DaysTemp(uiCurrentState.weather?.tempNormal, "Current")
            DaysTemp(uiCurrentState.weather?.tempMax, "max")
        }
    }
}
