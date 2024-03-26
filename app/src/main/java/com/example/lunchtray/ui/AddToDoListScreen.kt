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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.lunchtray.Audio

import com.example.lunchtray.R

@Composable
fun AddToDoListScreen(
    modifier: Modifier = Modifier
    ) {
    Column(modifier = modifier) {
        AddToDoListButtonGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
fun AddToDoListButtonGroup(
    modifier: Modifier = Modifier,
    ) {

    var entryName by remember {
        mutableStateOf("Entry Name")
    }

    var itemName by remember {
        mutableStateOf("Item Name")
    }

    var showMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
    )
    {
        TextField(value = entryName, onValueChange = {entryName = it} )

        Button(onClick = { showMenu = !showMenu }) {
            Text(text = "Select Location")
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }) {
            DropdownMenuItem(text = { Text("Load") }, onClick = { showMenu = false })
            DropdownMenuItem(text = { Text("Load") }, onClick = { showMenu = false })
            DropdownMenuItem(text = { Text("Load") }, onClick = { showMenu = false })
        }


    }

    var myAudio :Audio = Audio()

    Row(
        modifier = Modifier
    )
    {

        Button(onClick = { myAudio.startRecording() }) {
            Text(text = "Record")
        }

        Button(onClick = { myAudio.stopPlaying() }) {
            Text(text = "Play")
        }

        Button(onClick = {
            myAudio.stopRecording()
            myAudio.startPlaying()
        }) {
            Text(text = "Stop")
        }

    }

    Column (
        modifier = Modifier
    )
    {
        Row(modifier = Modifier)
        {
            Checkbox(checked = false, onCheckedChange = {})
            TextField(value = itemName, onValueChange = { itemName = it })
        }
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