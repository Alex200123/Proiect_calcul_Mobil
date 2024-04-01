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

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.lunchtray.R
import com.example.lunchtray.model.LocationData
import com.example.lunchtray.model.MenuItem


@Composable
fun DataBaseLocationsColumn(
    item: MutableList<LocationData>,
    string_test: MutableList<String>,
    modifier: Modifier = Modifier,
    onDetailsButtonClicked: () -> Unit ,
) {
    for (i in string_test) {
        Text(
//            text = i.locationName,
            text = i,
            style = MaterialTheme.typography.headlineSmall
        )


            Button(
                modifier = modifier,
                // the button is enabled when the user makes a selection

                onClick = onDetailsButtonClicked,

                ) {
                Text(text = "Details")
            }
            Button(
                modifier = modifier,
                // the button is enabled when the user makes a selection

                onClick = onDetailsButtonClicked,

                ) {
                Text(text = "Delete")
            }

    }


}

@Composable
fun DataBaseLocationsColumnNoDetails(
    item: MutableList<LocationData>,
    string_test: MutableList<String>,
    modifier: Modifier,
) {
    var iterator = 0
    for (i in string_test) {
        Text(
//            text = i.locationName,
            text = i,
            style = MaterialTheme.typography.headlineSmall
        )


    }


}



@Composable
fun BaseMenuScreen(
    locations: MutableList<LocationData>,
    string_test: MutableList<String>,
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit ,
    onDetailsButtonClicked: () -> Unit ,
) {
    Column(modifier = modifier) {


        MenuScreenButtonGroup(

            onNextButtonClicked = {
                // Assert not null bc next button is not enabled unless selectedItem is not null.
                onNextButtonClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )


            DataBaseLocationsColumn(
                item = locations,
                string_test = string_test,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                onDetailsButtonClicked = onDetailsButtonClicked
            )


    }


}


@Composable
fun DetailsScreen(
    locations: MutableList<LocationData>,
    string_test: MutableList<String>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {


        DataBaseLocationsColumnNoDetails(
            item = locations,
            string_test = string_test,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),

        )


    }


}



@Composable
fun MenuScreenButtonGroup(

    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,

) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){

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
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
    ) {


    Column(modifier = modifier) {

        MenuScreenButtonGroupAdd(


            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            onSubmitButtonClicked = onSubmitButtonClicked
        )
    }
}

var locationName: MutableState<String> = mutableStateOf("Location Name")
var addressName:  MutableState<String> = mutableStateOf("Address Name")
var maxAttendees: MutableState<String> = mutableStateOf("Max Attendees")
var hours:  MutableState<String> = mutableStateOf("Hours")
var days:  MutableState<String> = mutableStateOf("Days")

@Composable
fun MenuScreenButtonGroupAdd(
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = locationName.value, onValueChange = { locationName.value = it } )
        }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = addressName.value, onValueChange = { addressName.value = it })
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = maxAttendees.value, onValueChange = { maxAttendees.value = it }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
    }

    val selectedOption = remember { mutableStateOf("") }

    Row {
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
        TextField(value = hours.value, onValueChange = { hours.value = it }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),  modifier = Modifier
            .width(100.dp)
            .height(50.dp))
        Spacer(modifier = Modifier.width(100.dp))
        TextField(value = days.value, onValueChange = { days.value = it }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),  modifier = Modifier
            .width(100.dp)
            .height(50.dp))
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ){
        Button(onClick =onSubmitButtonClicked) {
            Text(text = "Submit")
        }
    }
  }

fun getLocationName(): String
{
    return locationName.value
}
fun getAddressName(): String
{
    return addressName.value
}
fun getMaxAttendees(): String
{
    return maxAttendees.value
}
fun getHours(): String
{
    return hours.value
}
fun getDays(): String
{
    return days.value
}

///////////////SignIn screen
@Composable
fun SignInMenu(
    options: List<MenuItem>,
    modifier: Modifier = Modifier,
    onSignUpButtonClicked: () -> Unit,
    onSubmitButtonClicked: () -> Unit,
    ) {

    var selectedItemName by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        options.forEach { item ->
            val onClick = {
                selectedItemName = item.name
            }

        }

        SignInButtonGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            onSignUpButtonClicked = onSignUpButtonClicked,
            onSubmitButtonClicked = onSubmitButtonClicked,
            )
    }
}

var email_signin: MutableState<String> = mutableStateOf("Email")
var password_signin:  MutableState<String> = mutableStateOf("Pass")

@Composable
fun SignInButtonGroup(
    modifier: Modifier = Modifier,
    onSignUpButtonClicked: () -> Unit,
    onSubmitButtonClicked: () -> Unit,
) {


    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = email_signin.value, onValueChange = { email_signin.value = it } )
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = password_signin.value, onValueChange = { password_signin.value = it })
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ){
        Button(onClick = onSubmitButtonClicked){
            Text(text = "Submit")
        }
        Button(onClick = onSignUpButtonClicked) {
            Text(text = "SignUp")
        }
    }
}

fun getEmailSignin(): String
{
    return email_signin.value
}

fun getPasswordSignin(): String
{
    return password_signin.value
}

///////////////SignUp screen
@Composable
fun SignUpMenu(
    options: List<MenuItem>,
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
    ) {

    var selectedItemName by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        options.forEach { item ->
            val onClick = {
                selectedItemName = item.name
            }

        }

        SignUpButtonGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            onSubmitButtonClicked = onSubmitButtonClicked,
            )
    }
}

var email: MutableState<String> = mutableStateOf("Email")
var password:  MutableState<String> = mutableStateOf("Pass")
var repeat_password :  MutableState<String> = mutableStateOf("Repeat")
@Composable
fun SignUpButtonGroup(
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
) {
//    var email by remember { mutableStateOf("Email") }
//    var password by remember { mutableStateOf("Password") }
//    var repeat_password by remember { mutableStateOf("Repeat Password") }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = email.value, onValueChange = { email.value = it } )
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = password.value, onValueChange = { password.value = it  })
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        TextField(value = repeat_password.value, onValueChange = { repeat_password.value = it  })
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ){
        Button(onClick = onSubmitButtonClicked) {
            Text(text = "Submit")
        }

    }
}

fun getEmail(): String
{
    return email.value
}

fun getPassword(): String
{
    return password.value
}

fun getRepeatPassword(): String
{
    return repeat_password.value
}