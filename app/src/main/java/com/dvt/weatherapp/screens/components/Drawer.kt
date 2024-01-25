package com.dvt.weatherapp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.R

enum class HomeDrawerOptions(
    val icon: Int,
    val title: String,

    ) {
    Home(
        icon = R.drawable.icon_home,
        title = "Home",
    ),
    Favourites(
        icon = R.drawable.icon_favourite,
        title = "Favourites",
    ),
    Map(
        icon = R.drawable.icon_map,
        title = "Map",
    ),
}

@Composable
fun HomeDrawerContent(
    onExit: () -> Unit,
    onClick: (HomeDrawerOptions) -> Unit,
    currentRoute: HomeDrawerOptions,
){
    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Header(
                onExit = onExit,
                modifier = Modifier.align(Alignment.End))

            Links(
                onClick = onClick,
                currentRoute = currentRoute,
            )
        }
    }
}

@Composable
private fun Header(onExit: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 10.dp)
            .padding(top = 20.dp, bottom = 20.dp, end = 5.dp),
    ) {
        IconButton(onClick = { onExit() }) {
            Icon(
                painter = painterResource(id = R.drawable.icon_close),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Composable
private fun Links(
    currentRoute: HomeDrawerOptions,
    onClick: (HomeDrawerOptions) -> Unit,
    modifier: Modifier = Modifier,
    routes: List<HomeDrawerOptions> = HomeDrawerOptions.entries,
    colors: NavigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = MaterialTheme.colorScheme.primary,
        selectedIconColor = MaterialTheme.colorScheme.primary,
        selectedTextColor = MaterialTheme.colorScheme.primary,
    ),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        routes.forEach { route ->
            val selected = route == currentRoute
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(route) }
                    .heightIn(30.dp)
                    .padding(start = 15.dp)
                    .padding(end = 15.dp)
                    .padding(vertical = 10.dp),
            ) {
                val iconColor = colors.iconColor(selected).value
                Icon(
                    painter = painterResource(route.icon),
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp),
                )

                val labelColor = colors.textColor(selected).value
                Text(
                    text = route.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = labelColor,
                )
            }
            if (selected) {
                HomeDivider(
                    thickness = 1.5.dp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.widthIn(150.dp),
                )
            }
        }
    }
}

@Composable
private fun HomeDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) {
    val targetThickness = if (thickness == Dp.Hairline) {
        (1f / LocalDensity.current.density).dp
    } else {
        thickness
    }
    Box(
        modifier
            .height(targetThickness)
            .background(color = color),
    )
}