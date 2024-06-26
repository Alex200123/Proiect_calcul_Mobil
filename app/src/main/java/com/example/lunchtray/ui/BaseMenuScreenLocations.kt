/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lunchtray.ui

import android.widget.EditText
import android.widget.RadioGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.lunchtray.R
import com.example.lunchtray.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun BaseMenuScreen(
    options: List<MenuItem>,
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
) {

    var selectedItemName by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        options.forEach { item ->
            val onClick = {
                selectedItemName = item.name
            }

        }

        MenuScreenButtonGroup(

            onCancelButtonClicked = onCancelButtonClicked,
            onNextButtonClicked = {
                // Assert not null bc next button is not enabled unless selectedItem is not null.
                onNextButtonClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}



@Composable
fun MenuScreenButtonGroup(

    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,

) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        OutlinedButton(modifier = Modifier.weight(1f), onClick = onCancelButtonClicked) {
            Text(stringResource(R.string.cancel).uppercase())
        }
        Button(
            modifier = Modifier.weight(1f),
            // the button is enabled when the user makes a selection

            onClick = onNextButtonClicked
        ) {
            Text(stringResource(R.string.next).uppercase())
        }
    }
}

@Composable
fun BaseMenuScreenAddLocations(
    options: List<MenuItem>,
    modifier: Modifier = Modifier,

    ) {

    var selectedItemName by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        options.forEach { item ->
            val onClick = {
                selectedItemName = item.name
            }

        }

        MenuScreenButtonGroupAdd(


            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),

        )
    }
}


@Composable
fun MenuScreenButtonGroupAdd(


    modifier: Modifier = Modifier,


) {
    var locationName by remember { mutableStateOf("Location Name") }
    var addressName by remember { mutableStateOf("Address Name") }
    var maxAttendees by remember { mutableStateOf("Max Attendees") }
    var hours by remember { mutableStateOf("Hours") }
    var days by remember { mutableStateOf("Days") }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = locationName, onValueChange = { locationName = it } )
        }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = addressName, onValueChange = { addressName = it })
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = maxAttendees, onValueChange = { maxAttendees = it }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
    }

    val selectedOption = remember { mutableStateOf("") }

    Column {
        RadioButton(
            selected = selectedOption.value == "Birthday",
            onClick = { selectedOption.value = "Birthday" }
        )
        Text("Birthday")

        RadioButton(
            selected = selectedOption.value == "Concert",
            onClick = { selectedOption.value = "Concert" }
        )
        Text("Concert")

        RadioButton(
            selected = selectedOption.value == "Party",
            onClick = { selectedOption.value = "Party" }
        )
        Text("Party")
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ){
        TextField(value = hours, onValueChange = { hours = it }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),  modifier = Modifier
            .width(100.dp)
            .height(50.dp))
        Spacer(modifier = Modifier.width(100.dp))
        TextField(value = days, onValueChange = { days = it }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),  modifier = Modifier
            .width(100.dp)
            .height(50.dp))
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ){
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Submit")
        }
    }
  }

//@Composable
//fun MenuScreenButtonGroupAdd(
//    modifier: Modifier = Modifier,
//    locationNameTextField: (String) -> Unit,
//) {
//    Row(
//        modifier = modifier,
//        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
//    ){
//
//        TextField(value = "LocationName", onValueChange = locationNameTextField)
//    }
//}