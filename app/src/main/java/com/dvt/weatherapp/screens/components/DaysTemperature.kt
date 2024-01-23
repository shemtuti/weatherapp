package com.dvt.weatherapp.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.dvt.weatherapp.R
import com.dvt.weatherapp.utils.Util

@Composable
fun DaysTemp(tempValue: Double?, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = String.format(
                Util.getFormatTemp(tempValue) + stringResource(R.string.temp_symbol)
            ),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}