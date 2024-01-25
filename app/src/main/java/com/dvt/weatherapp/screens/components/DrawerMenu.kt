package com.dvt.weatherapp.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.R

@Composable
fun DrawerButton(
    onToggleDrawer: () -> Unit) {

    Box(
        Modifier
            .padding(start = 10.dp, top = 10.dp, bottom = 5.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_menu),
            contentDescription = null,
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.CenterStart)
                .clickable {
                    onToggleDrawer()
            },
        )
    }
}