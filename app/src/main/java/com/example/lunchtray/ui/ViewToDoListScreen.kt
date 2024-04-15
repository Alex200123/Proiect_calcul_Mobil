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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lunchtray.R
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.model.MenuItem
import com.example.lunchtray.model.TaskData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun MenuButton(

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
            Text(stringResource(R.string.AddNewToDo).uppercase())
        }
    }
}

@Composable
fun DataBaseToDosColumn(
    item: MutableList<TaskData>,
    ToDoLists: MutableList<String>,
    modifier: Modifier = Modifier,
    onDeleteButtonClicked: () -> Unit
) {
    var iterator = 0
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        for (i in ToDoLists) {
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
                            var tempItr by remember {
                                mutableIntStateOf(iterator)
                            }


                            Text(
                                text = "ToDo: ${item[iterator].taskName}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Location Name: ${item[iterator].location}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            var temp by remember {
                                mutableStateOf(false)
                            }

                            println(item.size)
                            for(myvar in item)
                            {
                                println(myvar.taskName)
                            }

                            for(task in item[iterator].tasks) {

                                Row {
                                    Checkbox(checked = temp, onCheckedChange = {
                                        temp = !temp
                                        val auth = FirebaseAuth.getInstance()
                                        if(temp) {
                                            var databaseRef =
                                                FirebaseDatabase.getInstance().reference.child("ToDo")
                                                    .child(auth.currentUser?.uid.toString())
                                                    .child(item[tempItr].taskName).child("Tasks")
                                                    .child(task).setValue(1)
                                        }
                                        else
                                        {
                                            var databaseRef =
                                                FirebaseDatabase.getInstance().reference.child("ToDo")
                                                    .child(auth.currentUser?.uid.toString())
                                                    .child(item[tempItr].taskName).child("Tasks")
                                                    .child(task).setValue(0)
                                        }

                                    })
                                    Text(
                                        text = "Task: $task",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }

                            }
                        }
                        Button(
                            onClick = {

                                val auth = FirebaseAuth.getInstance()
                                val databaseRef = FirebaseDatabase.getInstance().reference
                                    .child("ToDo")
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
fun ViewToDoListScreen(
    ToDos: MutableList<TaskData>,
    ToDoList: MutableList<String>,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteButtonClicked: () -> Unit
) {


    Column(modifier = modifier) {
        MenuButton(
            onNextButtonClicked = {
                // Assert not null bc next button is not enabled unless selectedItem is not null.
                onNextButtonClicked()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )

        DataBaseToDosColumn(
            item = ToDos,
            ToDoLists = ToDoList,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            onDeleteButtonClicked = onDeleteButtonClicked
        )

    }



}
