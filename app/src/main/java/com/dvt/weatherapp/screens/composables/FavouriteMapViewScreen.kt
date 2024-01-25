package com.dvt.weatherapp.screens.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dvt.weatherapp.screens.components.DrawerButton
import com.dvt.weatherapp.screens.viewmodels.FavouriteViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteMapViewScreen(
    onToggleDrawer: () -> Unit,
    viewModel: FavouriteViewModel = koinViewModel()
) {
    val uiFavouriteState = viewModel.stateFavourite.collectAsState().value
    //val mapView = rememberma

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        DrawerButton(onToggleDrawer = onToggleDrawer)

/*        AndroidView({ mapView }) { mapView ->
            LaunchedEffect(uiFavouriteState.favourite) {
                val map = mapView.awaitMap()
                map.uiSettings.isZoomControlsEnabled = true

                if (uiFavouriteState.favourite.isNotEmpty()) {
                    uiFavouriteState.favourite.forEach { weather ->
                        val location = LatLng(weather.lat ?: 0.0, weather.lon ?: 0.0)
                        val markerOptions = MarkerOptions()
                            .title(weather.locationName)
                            .snippet(
                                buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(weather.currentWeatherDesc)
                                    }
                                    append(" with temp:")
                                    withStyle(style = SpanStyle(color = Color.Red)) {
                                        append("${weather.tempNormal}${stringResource(R.string.temp_symbol)}")
                                    }
                                }.toString()
                            )
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map_pin))
                            .position(location)
                        map.addMarker(markerOptions)
                    }

                    val firstItem = uiFavouriteState.favourite[0]
                    val location2 = LatLng(firstItem.lat ?: 0.0, firstItem.lon ?: 0.0)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(location2, 15.0f))
                }
            }
        }*/
    }
}
