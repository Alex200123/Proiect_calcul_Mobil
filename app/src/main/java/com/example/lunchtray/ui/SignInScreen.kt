package com.example.lunchtray.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lunchtray.model.MenuItem


@Composable
fun SignInScreen(
    options: List<MenuItem.SideDishItem>,
    onSignUpButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
    ) {
    SignInMenu(
        options = options,


        modifier = modifier,
        onSignUpButtonClicked = onSignUpButtonClicked,
        onSubmitButtonClicked = onSubmitButtonClicked,
        )
}
