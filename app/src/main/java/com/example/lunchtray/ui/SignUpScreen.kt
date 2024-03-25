package com.example.lunchtray.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lunchtray.model.MenuItem


@Composable
fun SignUpScreen(
    options: List<MenuItem.SideDishItem>,
    onSubmitButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    SignUpMenu(
        options = options,


        modifier = modifier,
        onSubmitButtonClicked = onSubmitButtonClicked
        )
}
