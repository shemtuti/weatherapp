package com.dvt.weatherapp.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvt.weatherapp.R
import com.dvt.weatherapp.screens.components.DrawerButton
import com.dvt.weatherapp.screens.components.FavouriteItem
import com.dvt.weatherapp.screens.viewmodels.FavouriteViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteWeather(
    onToggleDrawer: () -> Unit,
    viewModel: FavouriteViewModel = koinViewModel()
) {
    val uiFavouriteState = viewModel.stateFavourite.collectAsState().value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        DrawerButton(onToggleDrawer  = onToggleDrawer)
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            if (uiFavouriteState.favourite.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_favourite),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.White),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        items = uiFavouriteState.favourite,
                        itemContent = { favourite ->
                            FavouriteItem(
                                favourite = favourite,
                            )
                        })
                }
            }
        }
    }
}