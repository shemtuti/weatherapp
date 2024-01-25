package com.dvt.weatherapp.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.R
import com.dvt.weatherapp.data.local.model.FavouriteTable
import com.dvt.weatherapp.screens.viewmodels.FavouriteViewModel
import com.dvt.weatherapp.utils.Util
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteItem(
    favourite: FavouriteTable,
    viewModel: FavouriteViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val weatherCurrent = favourite.currentWeather
    val color = Util.getWeatherBackgroundColor(weatherCurrent)

    Card(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(500.dp),
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = color)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                        Text(
                            text = Util.getFormatTemp(favourite.tempNormal) + stringResource(R.string.temp_symbol),
                            style = MaterialTheme.typography.displayLarge,
                        )
                        Text(
                            text = favourite.currentWeatherDesc,
                            style = MaterialTheme.typography.displaySmall,
                        )
                    }
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = rememberDrawablePainter(
                            drawable = Util.getWeatherIconDrawable(
                                context = context,
                                favourite.currentWeather
                            )
                        ),
                        contentDescription = stringResource(R.string.location_icon),
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                        Text(
                            text = favourite.locationName,
                            style = MaterialTheme.typography.displayMedium,
                        )
                    }
                    Column(verticalArrangement = Arrangement.Bottom) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = stringResource(R.string.delete_favourite),
                            tint = Color(0xFFd68118),
                            modifier = Modifier.size(20.dp).clickable {
                                viewModel.deleteFavourite(favourite)
                            })
                    }
                }
            }
        }
    }
}