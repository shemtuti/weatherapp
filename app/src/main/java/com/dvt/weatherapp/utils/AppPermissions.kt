package com.dvt.weatherapp.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.dvt.weatherapp.R
import java.util.Objects

object AppPermissions {

    /**
     * Check Internet connection permission
     */
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    /**
     * Check Fine location permission
     */

    fun hasPermissions(
        context: Context?,
        vararg permissions: String,
    ): Boolean {
        if (context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission,
                    ) !== PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun validateAndForceLocationSetting(context: FragmentActivity) {
        if (!isLocationEnabled(context)) {
            checkIfLocationIsEnabledDialog(context)
        }
        if (!hasPermissions(
                context,
                Constants.permissionsLocation[0],
                Constants.permissionsLocation[1],
            )
        ) {
            ActivityCompat.requestPermissions(
                Objects.requireNonNull(context),
                Constants.permissionsLocation,
                1,
            )
        }
    }

    private fun isLocationEnabled(context: Context): Boolean {
        var locationMode = 0
        val locationProviders: String
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(
                    context.contentResolver,
                    Settings.Secure.LOCATION_MODE,
                )
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
            }
            locationMode != Settings.Secure.LOCATION_MODE_OFF
        } else {
            locationProviders = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED,
            )
            !TextUtils.isEmpty(locationProviders)
        }
    }

    private fun checkIfLocationIsEnabledDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setIcon(R.drawable.ic_no_location)
        builder.setTitle(context.getString(R.string.no_loc_title))
        builder.setMessage(
            context.getString(R.string.no_loc_message),
        )
        builder.setCancelable(false)
        builder.setPositiveButton(
            context.getString(R.string.loc_btn_agree),
        ) { dialog, which ->
            dialog.dismiss()
            val myIntent = Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS,
            )
            context.startActivity(myIntent)
        }
        builder.show()
    }
}
