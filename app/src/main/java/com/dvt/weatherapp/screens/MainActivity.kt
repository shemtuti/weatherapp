package com.dvt.weatherapp.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import com.dvt.weatherapp.R
import com.dvt.weatherapp.screens.components.HomeDrawerContent
import com.dvt.weatherapp.screens.components.HomeDrawerOptions
import com.dvt.weatherapp.screens.composables.FavouriteMap
import com.dvt.weatherapp.screens.composables.FavouriteWeather
import com.dvt.weatherapp.screens.composables.MainScreen
import com.dvt.weatherapp.screens.viewmodels.WeatherViewModel
import com.dvt.weatherapp.ui.theme.weatherAppTheme
import com.dvt.weatherapp.utils.AppPermissions.hasPermissions
import com.dvt.weatherapp.utils.AppPermissions.isInternetAvailable
import com.dvt.weatherapp.utils.AppPermissions.validateAndForceLocationSetting
import com.dvt.weatherapp.utils.AppPreferences
import com.dvt.weatherapp.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by inject()
    private val appPreference: AppPreferences by inject()
    private lateinit var client: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConnected: Boolean = isInternetAvailable(this)
        if (isConnected) {
            setContent {
                HomeScreen(this)
            }
        } else {
            setContent {
                InternetCheck(this)

                // NoInternetConnection(this)
            }
        }
    }

    @Composable
    fun HomeScreen(context: Context) {
        val coroutineScope = rememberCoroutineScope()
        var drawerRoute by remember { mutableStateOf(HomeDrawerOptions.Home) }
        val drawerState = rememberDrawerState(DrawerValue.Closed)

        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    HomeDrawerContent(
                        onExit = {
                            coroutineScope.launch { drawerState.close() }
                        },
                        onClick = {
                            drawerRoute = it
                            coroutineScope.launch { drawerState.close() }
                        },
                        currentRoute = drawerRoute,
                    )
                }
            },
            drawerState = drawerState,
        ) {
            when (drawerRoute) {
                HomeDrawerOptions.Home -> {
                    weatherAppTheme {
                        MainScreen(onToggleDrawer = {
                            coroutineScope.launch {
                                if (drawerState.isOpen) drawerState.close() else drawerState.open()
                            }
                        })
                    }
                }
                HomeDrawerOptions.Favourites -> {
                    weatherAppTheme {
                        FavouriteWeather(onToggleDrawer = {
                            coroutineScope.launch {
                                if (drawerState.isOpen) drawerState.close() else drawerState.open()
                            }
                        })
                    }
                }
                HomeDrawerOptions.Map -> {
                    weatherAppTheme {
                        FavouriteMap(onToggleDrawer = {
                            coroutineScope.launch {
                                if (drawerState.isOpen) drawerState.close() else drawerState.open()
                            }
                        })
                    }
                }
            }
        }
        BackHandler(enabled = drawerRoute != HomeDrawerOptions.Home) {
            drawerRoute = HomeDrawerOptions.Home
            if (drawerState.isOpen) coroutineScope.launch { drawerState.close() }
        }
    }

    fun fetchWeatherData() {
        if (hasLocationPermission()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            client.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Get latitude and longitude
                    val lat = location?.latitude.toString()
                    val lon = location?.longitude.toString()

                    Timber.i("##Lat: $lat")
                    Timber.i("##Lon: $lon")

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
        // checkIfLocationPermissionIsEnabled(this)

        checkAndRequestLocationPermission()
        validateAndForceLocationSetting(this)
        client = LocationServices.getFusedLocationProviderClient(this)
        fetchWeatherData()
    }

    override fun onResume() {
        super.onResume()
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
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ),
            Constants.PERMISSION_REQUEST_LOCATION,
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
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
private fun InternetCheck(context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setIcon(R.drawable.ic_no_internet)
    builder.setTitle(context.getString(R.string.no_internet_title))
    builder.setMessage(
        context.getString(R.string.no_internet_message),
    )
    builder.setCancelable(true)
    builder.setPositiveButton(
        context.getString(R.string.loc_btn_agree),
    ) { dialog, which ->
        dialog.dismiss()
        val myIntent = Intent(
            Settings.ACTION_WIRELESS_SETTINGS,
        )
        context.startActivity(myIntent)

        (context as MainActivity).setContent {
            context.HomeScreen(context)
        }
    }
    builder.setNegativeButton(
        context.getString(R.string.loc_btn_disagree),
    ) { dialog, which ->
        dialog.dismiss()
        // Update the app's content to display the HomeScreen composable
        (context as MainActivity).setContent {
            context.HomeScreen(context)
        }
    }
    builder.show()
}
