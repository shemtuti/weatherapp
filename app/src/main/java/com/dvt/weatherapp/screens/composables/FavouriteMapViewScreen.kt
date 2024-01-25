package com.dvt.weatherapp.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dvt.weatherapp.R
import com.dvt.weatherapp.screens.components.DrawerButton
import com.dvt.weatherapp.screens.components.rememberFavouriteMapViewWithLifecycle
import com.dvt.weatherapp.screens.viewmodels.FavouriteViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteMap(
    onToggleDrawer: () -> Unit,
    viewModel: FavouriteViewModel = koinViewModel()
) {
    val uiFavouriteState = viewModel.stateFavourite.collectAsState().value
    val mapView = rememberFavouriteMapViewWithLifecycle()
    var map : GoogleMap? by remember { mutableStateOf(null) }

    LaunchedEffect(uiFavouriteState.favourite) {
        map = mapView.awaitMap()
        map?.uiSettings?.isZoomControlsEnabled = true
        map?.mapType = GoogleMap.MAP_TYPE_NORMAL
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        DrawerButton(onToggleDrawer = onToggleDrawer)

        Text(
            text = stringResource(R.string.favourite_title_map),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp, bottom = 10.dp)
        )

        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            AndroidView({ mapView }) { mapView ->
                if (!uiFavouriteState.favourite.equals("[]")) {
                    map?.clear()
                    var firstMarker = true
                    var bounds: LatLngBounds? = null

                    if (uiFavouriteState.favourite.isNotEmpty()) {
                        uiFavouriteState.favourite.forEach { weather ->
                            val location = LatLng(weather.lat ?: 0.0, weather.lon ?: 0.0)

                            if (firstMarker) {
                                bounds = LatLngBounds.builder().include(location).build()
                                firstMarker = false
                            } else {
                                bounds = bounds?.including(location)
                            }

                            val markerOptions = MarkerOptions()
                                .title(weather.locationName)
                                .snippet(weather.currentWeatherDesc)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker))
                                .position(location)
                            map?.addMarker(markerOptions)
                        }

                        bounds?.let { validBounds ->
                            val padding = 40

                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngBounds(validBounds, padding)
                            )
                        }
                    }
                }
            }
        }
    }
}
