package com.dvt.weatherapp.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.text.style.TextAlign
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
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        DrawerButton(onToggleDrawer  = onToggleDrawer)

        Text(
            text = stringResource(R.string.favourite_title),
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
            if (uiFavouriteState.favourite.equals("[]")) {
                Text(
                    text = stringResource(R.string.no_favourite),
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
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