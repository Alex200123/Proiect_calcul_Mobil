package com.example.lunchtray.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier



@Composable
fun SignInScreen(

    onSignUpButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
    ) {
    SignInMenu(


        modifier = modifier,
        onSignUpButtonClicked = onSignUpButtonClicked,
        onSubmitButtonClicked = onSubmitButtonClicked,
        )
}
