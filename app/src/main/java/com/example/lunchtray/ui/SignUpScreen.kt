package com.example.lunchtray.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier



@Composable
fun SignUpScreen(

    onSubmitButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    SignUpMenu(



        modifier = modifier,
        onSubmitButtonClicked = onSubmitButtonClicked
        )
}
