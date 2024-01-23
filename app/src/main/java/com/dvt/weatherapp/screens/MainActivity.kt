package com.dvt.weatherapp.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.dvt.weatherapp.R
import com.dvt.weatherapp.screens.composables.MainScreen
import com.dvt.weatherapp.screens.viewmodels.WeatherViewModel
import com.dvt.weatherapp.ui.theme.WeatherAppTheme
import com.dvt.weatherapp.utils.AppPermissions.hasPermissions
import com.dvt.weatherapp.utils.AppPermissions.isInternetAvailable
import com.dvt.weatherapp.utils.AppPermissions.validateAndForceLocationSetting
import com.dvt.weatherapp.utils.AppPreferences
import com.dvt.weatherapp.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by inject()
    private val appPreference: AppPreferences by inject()
    private lateinit var client: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConnected: Boolean = isInternetAvailable(this)

        if(isConnected) {
            setContent {
                HomeScreen(this)
            }
        }
        else {
            setContent {
                NoInternetConnection(this)
            }
        }
    }

    @Composable
    fun HomeScreen(context: Context) {
        WeatherAppTheme {
            MainScreen()
        }
    }

    private fun fetchWeatherData() {
        if (hasLocationPermission()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            client.lastLocation
                .addOnSuccessListener { location: Location? ->
                // Get latitude and longitude
                val lat = location?.latitude.toString()
                val lon = location?.longitude.toString()

                Log.e("##Lat", lat)
                Log.e("##Lon", lon)

                // Save to datasource
                viewModel.saveLat(lat)
                viewModel.saveLon(lon)

                viewModel.getRemoteCurrentWeather(lat, lon)
                viewModel.getRemoteForecastWeather(lat, lon)
            }
        } else {
            requestLocationPermission()
        }
    }

    override fun onStart() {
        super.onStart()
        //checkIfLocationPermissionIsEnabled(this)

        checkAndRequestLocationPermission()
        validateAndForceLocationSetting(this)
        client = LocationServices.getFusedLocationProviderClient(this)
        fetchWeatherData()
    }

    override fun onResume() {
        super.onResume()
        //validateAndForceLocationSetting(this)
        //checkAndRequestLocationPermission()

        checkAndRequestLocationPermission()
        validateAndForceLocationSetting(this)
        client = LocationServices.getFusedLocationProviderClient(this)
        fetchWeatherData()
    }

    private fun checkAndRequestLocationPermission() {
        if (!hasLocationPermission()) {
            requestLocationPermission()
        }
    }

    private fun hasLocationPermission(): Boolean {
        return hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),
            Constants.PERMISSION_REQUEST_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PERMISSION_REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, fetch weather data
                fetchWeatherData()
            } else {
                // Permission denied, handle accordingly
                // You might want to inform the user or take appropriate action
            }
        }
    }
}

@Composable
fun NoInternetConnection(context: Context) {
    WeatherAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val imagePainter = painterResource(id = R.drawable.ic_no_internet)

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp)
                        .size(40.dp),
                    painter = imagePainter,
                    contentDescription = "No Internet Icon",
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    stringResource(R.string.no_internet_connection),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                InternetCheckButton(context)
            }
        }
    }
}

@Composable
private fun InternetCheckButton(context: Context) {
    TextButton(
        onClick = {
            val isConnected: Boolean = isInternetAvailable(context)
            if (isConnected) {
                // Update the app's content to display the HomeScreen composable
                (context as MainActivity).setContent {
                    context.HomeScreen(context)
                }
            } else {
                // Update the app's content to display the NoConnection composable
                (context as MainActivity).setContent {
                    NoInternetConnection(context)
                }
            }
        },
        modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.textButtonColors(containerColor = Color(0xFF54717A)),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(
            text = stringResource(R.string.try_again),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
    }
}


