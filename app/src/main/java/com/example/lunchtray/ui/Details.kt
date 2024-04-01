package com.example.lunchtray.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lunchtray.model.LocationData

@Composable
fun DetailsMenuScreen(
    locations: MutableList<LocationData>,
    string_test: MutableList<String>,
    modifier: Modifier = Modifier,

) {
    DetailsScreen(
        locations = locations,
        string_test = string_test,
        modifier = modifier,

    )
}

