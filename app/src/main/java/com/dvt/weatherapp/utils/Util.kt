package com.dvt.weatherapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.content.res.ResourcesCompat
import com.dvt.weatherapp.R
import com.dvt.weatherapp.ui.theme.Cloudy
import com.dvt.weatherapp.ui.theme.Rainy
import com.dvt.weatherapp.ui.theme.Sunny
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.math.round

class Util {
    companion object {
        fun convertToDateTime(unixTimestamp: Int): String {
            val instant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Instant.ofEpochSecond(unixTimestamp.toLong())
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)

            val formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")
            return dateTime.format(formatter)
        }

        fun convertToDate(unixTimestamp: Int): String {
            val instant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Instant.ofEpochSecond(unixTimestamp.toLong())
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)

            val formatter = DateTimeFormatter.ofPattern("yyy-MM-dd")
            return dateTime.format(formatter)
        }

        fun convertToDayOfWeek(unixTimestamp: Int): String {
            val instant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Instant.ofEpochSecond(unixTimestamp.toLong())
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)

            val formatter = DateTimeFormatter.ofPattern("EEEE")

            return dateTime.format(formatter)
        }

        fun convertHourOfDay(unixTimestamp: Int): String {
            val instant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Instant.ofEpochSecond(unixTimestamp.toLong())
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)

            val formatter = DateTimeFormatter.ofPattern("HH:mm")

            return dateTime.format(formatter)
        }

        fun getDayOfWeekFromUTC(dateString: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            return try {
                val date = dateFormat.parse(dateString)
                val dayFormat = SimpleDateFormat("EEEE", Locale.US)
                dayFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                "Error parsing date"
            }
        }

        @Composable
        fun getWeatherBackgroundColor(weather: String?): Color {
            val backgroundColor: Color = when (weather) {
                "Clouds" -> Cloudy
                "Rain" -> Rainy
                else -> Sunny
            }
            return backgroundColor
        }

        fun getWeatherBackgroundDrawable(context: Context, mainDescription: String?): Drawable? {
            val weatherDrawable: Drawable? = when (mainDescription) {
                "Clouds" -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.sea_cloudy,
                    null,
                )

                "Rain" -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.sea_rainy,
                    null,
                )

                else -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.sea_sunny,
                    null,
                )
            }
            return weatherDrawable
        }

        fun getWeatherIconDrawable(context: Context, mainDescription: String?): Drawable? {
            val weatherDrawable: Drawable? = when (mainDescription) {
                "Clouds" -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.mipmap.ic_partlysunny,
                    null,
                )

                "Rain" -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.mipmap.ic_rain,
                    null,
                )

                else -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.mipmap.ic_clear,
                    null,
                )
            }
            return weatherDrawable
        }

        fun getLocationIconDrawable(context: Context, mainDescription: String?): Drawable? {
            val weatherDrawable: Drawable? = when (mainDescription) {
                "Clouds" -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.marker_cloudy,
                    null,
                )

                "Rain" -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.marker_rainy,
                    null,
                )

                else -> ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.marker_sunny,
                    null,
                )
            }
            return weatherDrawable
        }

        fun getTimeOfDataCalculation(timeString: Int?): String {
            val sdf = SimpleDateFormat("hh:mm a")
            val dateFormat: Date = Date(timeString.toString().toLong() * 1000)
            val dateTime: String = sdf.format(dateFormat)
            return dateTime
        }

        fun getFormatTemp(temp: Double?): String {
            val newTemp = round(temp ?: 0.0)
            val formattedTemp: String = newTemp.toInt().toString()
            return formattedTemp
        }

        fun getCurrentDayOfTheWeek(): String {
            val sdf = SimpleDateFormat("EEEE")
            val d = Date()
            return sdf.format(d)
        }
    }
}
