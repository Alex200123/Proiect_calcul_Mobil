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

import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.lunchtray.R
import com.example.lunchtray.model.LocationData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


@Composable
fun DataBaseLocationsColumn(
    item: MutableList<LocationData>,
    string_test: MutableList<String>,
    modifier: Modifier = Modifier,
    onDetailsButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit
) {
    var iterator = 0
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        for (i in string_test) {
            Card(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = i,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Location Name: ${item[iterator].locationName}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Address: ${item[iterator].addressName}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Max Attendees: ${item[iterator].maxAttendees}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Hours: ${item[iterator].hours}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Days: ${item[iterator].days}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Button(
                            onClick = {

                                val auth = FirebaseAuth.getInstance()
                                val databaseRef = FirebaseDatabase.getInstance().reference
                                    .child("Locations")
                                    .child(auth.currentUser?.uid.toString())
                                    .child(i)

                                databaseRef.removeValue().addOnSuccessListener {
                                    onDeleteButtonClicked()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            Text(text = "Delete")
                        }
                    }
                }
            }
            iterator++
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
    onDeleteButtonClicked: () -> Unit
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
                onDetailsButtonClicked = onDetailsButtonClicked,
                onDeleteButtonClicked = onDeleteButtonClicked
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


            onClick = onNextButtonClicked
        ) {
            Text(stringResource(R.string.nextlocation).uppercase())
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

var locationName: MutableState<String> = mutableStateOf("")
var addressName:  MutableState<String> = mutableStateOf("")
var maxAttendees: MutableState<String> = mutableStateOf("")
var hours:  MutableState<String> = mutableStateOf("")
var days:  MutableState<String> = mutableStateOf("")

@Composable
fun MenuScreenButtonGroupAdd(
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
) {

    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            TextField(
                value = locationName.value,
                onValueChange = { locationName.value = it },
                placeholder = { Text("Location Name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = addressName.value,
                onValueChange = { addressName.value = it },
                placeholder = { Text("Address Name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = maxAttendees.value,
                onValueChange = { maxAttendees.value = it },
                placeholder = { Text("Max Attendees") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = hours.value,
                    onValueChange = { hours.value = it },
                    placeholder = { Text("Hours per day") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                )
                TextField(
                    value = days.value,
                    onValueChange = { days.value = it },
                    placeholder = { Text("Days") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onSubmitButtonClicked,
                    modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(text = "Submit")
                }
            }
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
    modifier: Modifier = Modifier,
    onSignUpButtonClicked: () -> Unit,
    onSubmitButtonClicked: () -> Unit,
    ) {

    var selectedItemName by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {


        SignInButtonGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            onSignUpButtonClicked = onSignUpButtonClicked,
            onSubmitButtonClicked = onSubmitButtonClicked,
            )
    }
}

var email_signin: MutableState<String> = mutableStateOf("gegia@gmail.com")
var password_signin:  MutableState<String> = mutableStateOf("gegia1")

@Composable
fun SignInButtonGroup(
    modifier: Modifier = Modifier,
    onSignUpButtonClicked: () -> Unit,
    onSubmitButtonClicked: () -> Unit,
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            TextField(
                value = email_signin.value,
                onValueChange = { email_signin.value = it },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = password_signin.value,
                onValueChange = { password_signin.value = it },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                placeholder = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        if (passwordVisibility) {
                            Text(text = "Hide")
                        } else {
                            Text(text = "Look")
                        }
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onSubmitButtonClicked,
                    modifier = Modifier.weight(1f).padding(horizontal = dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(text = "Submit")
                }
                Button(
                    onClick = onSignUpButtonClicked,
                    modifier = Modifier.weight(1f).padding(horizontal = dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(text = "SignUp")
                }
            }
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
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
    ) {



    Column(modifier = modifier) {

        SignUpButtonGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            onSubmitButtonClicked = onSubmitButtonClicked,
            )
    }
}

var email: MutableState<String> = mutableStateOf("")
var password:  MutableState<String> = mutableStateOf("")
var repeat_password :  MutableState<String> = mutableStateOf("")

@Composable
fun SignUpButtonGroup(
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                placeholder = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        if (passwordVisibility) {
                            Text(text = "Hide")
                        } else {
                            Text(text = "Look")
                        }
                    }
                }
            )
            TextField(
                value = repeat_password.value,
                onValueChange = { repeat_password.value = it },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                placeholder = { Text("Repeat Password") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        if (passwordVisibility) {
                            Text(text = "Hide")
                        } else {
                            Text(text = "Look")
                        }
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onSubmitButtonClicked,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(text = "Submit")
                }
            }
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