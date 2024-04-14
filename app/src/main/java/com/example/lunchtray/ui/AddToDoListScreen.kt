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
import android.content.Context
import android.provider.ContactsContract.Data
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.lunchtray.Audio

import com.example.lunchtray.R
import com.example.lunchtray.model.LocationData
import com.google.firebase.database.DataSnapshot
import java.io.File

var entryName: MutableState<String> = mutableStateOf("")
var itemName: MutableState<String> = mutableStateOf("")
var selectedLocation: MutableState<String> = mutableStateOf("Please select location!")

@Composable
fun AddToDoListScreen(
    locations : MutableList<String>,
    context: Context,
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit
    ) {
    Column(modifier = modifier) {
        AddToDoListButtonGroup(
            onSubmitButtonClicked = onSubmitButtonClicked,
            locations = locations,
            context = context,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
fun AddToDoListButtonGroup(
    locations : MutableList<String>,
    context: Context,
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
    ) {

    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = modifier
        )
        {
            TextField(
                value = entryName.value,
                onValueChange = { entryName.value = it },
                placeholder = { Text("ToDo") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = { showMenu = !showMenu }) {
                Text(text = selectedLocation.value)
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }) {

                locations.forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = {
                        selectedLocation.value = it
                        showMenu = false
                    })
                }

            }


        }

        var myAudio: Audio = Audio()

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center
        )
        {
            Button(onClick = {
                myAudio.startRecording(
                    File(
                        context.cacheDir,
                        R.string.audioFile.toString()
                    )
                )
            }) {
                Text(text = "Record")
            }

            Button(onClick = {
                myAudio.startPlaying(
                    context,
                    File(context.cacheDir, R.string.audioFile.toString())
                )
            }) {
                Text(text = "Play")
            }

            Button(onClick = {
                myAudio.stopRecording()
                myAudio.stopPlaying()
            }) {
                Text(text = "Stop")
            }

        }

        Row(
            modifier = modifier
        )
        {
            Checkbox(checked = false, onCheckedChange = {})
            TextField(value = itemName.value,
                onValueChange = { itemName.value = it },
                placeholder = { Text("ToDO Item") }
            )
        }

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = onSubmitButtonClicked) {
                Text(text = "Submit")
            }
        }
    }
}

fun getEntryName(): String
{
    return entryName.value
}

fun getItemName(): String
{
    return itemName.value
}

fun getSelectedLocation(): String
{
    return selectedLocation.value
}

