
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
package com.example.lunchtray

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.model.LocationData
import com.example.lunchtray.ui.ViewLocationsScreen
import com.example.lunchtray.ui.AddLocationMenuScreen
import com.example.lunchtray.ui.AddToDoListScreen
import com.example.lunchtray.ui.DetailsMenuScreen
import com.example.lunchtray.ui.SignInScreen
import com.example.lunchtray.ui.SignUpScreen
import com.example.lunchtray.ui.StartOrderScreen
import com.example.lunchtray.ui.ViewToDoListScreen
import com.example.lunchtray.ui.getAddressName
import com.example.lunchtray.ui.getDays
import com.example.lunchtray.ui.getEmail
import com.example.lunchtray.ui.getEmailSignin
import com.example.lunchtray.ui.getHours
import com.example.lunchtray.ui.getLocationName
import com.example.lunchtray.ui.getMaxAttendees
import com.example.lunchtray.ui.getPassword
import com.example.lunchtray.ui.getPasswordSignin
import com.example.lunchtray.ui.getRepeatPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


enum class ToDoAppScreen(@StringRes val title: Int) {
    SignIn(title = R.string.signin),
    SignUp(title = R.string.signup),
    Start(title = R.string.app_name),
    Entree(title = R.string.choose_entree),
    SideDish(title = R.string.choose_side_dish),
    Accompaniment(title = R.string.choose_accompaniment),
    Checkout(title = R.string.order_checkout),
    ToDoView(title = R.string.ToDoView),
    ToDoAdd(title = R.string.ToDoAdd),
    Details(title =  R.string.Details)
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreenTitle)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}




@Composable
fun ToDoApp(applicationContext: Context) {

    var context: Context = applicationContext

    //Create NavController
    val navController = rememberNavController()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = ToDoAppScreen.valueOf(
        backStackEntry?.destination?.route ?: ToDoAppScreen.Start.name
    )
    var nodesInDatabase: MutableList<LocationData> = mutableListOf<LocationData>()
    var string_test: MutableList<String> = mutableListOf<String>()
    // Create ViewModel


    Scaffold(
        topBar = {
            ToDoAppBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->


        NavHost(
            navController = navController,
            startDestination = ToDoAppScreen.SignIn.name,
        ) {
            composable(route = ToDoAppScreen.SignIn.name) {
                SignInScreen(
                    options = DataSource.sideDishMenuItems,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding), 
                    onSignUpButtonClicked = {
                        navController.navigate(ToDoAppScreen.SignUp.name)
                    },
                    onSubmitButtonClicked = {
                        val email:String = getEmailSignin().trim()
                        val password: String = getPasswordSignin().trim()


                        val auth:FirebaseAuth

                        auth = FirebaseAuth.getInstance()

                        if(email.isNotEmpty() && password.isNotEmpty())
                        {
                            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                                {
                                    if(it.isSuccessful)
                                    {
                                        navController.navigate(ToDoAppScreen.Start.name)
                                    }
                                })


                        }
                    }

                )
            }

            composable(route = ToDoAppScreen.SignUp.name) {
                SignUpScreen(
                    options = DataSource.sideDishMenuItems,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onSubmitButtonClicked = {
                        val email:String = getEmail().trim()
                        val password: String = getPassword().trim()
                        val repeat_password: String = getRepeatPassword().trim()

                        var auth:FirebaseAuth

                        auth = FirebaseAuth.getInstance()


                        if(email.isNotEmpty() && password.isNotEmpty() && repeat_password.isNotEmpty())
                        {
                            if(password == repeat_password)
                            {
                                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(
                                    {
                                        if(it.isSuccessful)
                                        {
                                            navController.navigate(ToDoAppScreen.Start.name)
                                        }
                                    })
                            }
                        }


                    }
                )
            }

            composable(route = ToDoAppScreen.ToDoView.name){
                ViewToDoListScreen(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding),
                    onCancelButtonClicked = { navController.navigate(ToDoAppScreen.Start.name) },
                    onNextButtonClicked = { navController.navigate(ToDoAppScreen.ToDoAdd.name)})
            }

            composable(route = ToDoAppScreen.ToDoAdd.name){
                AddToDoListScreen(
                    context,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            composable(route = ToDoAppScreen.Start.name) {


                val auth:FirebaseAuth
                val databaseRef:DatabaseReference




                auth = FirebaseAuth.getInstance()

                databaseRef = FirebaseDatabase.getInstance().reference.
                child("Locations").
                child(auth.currentUser?.uid.toString())




                databaseRef.addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        string_test.clear()
                        for(locationSnapshot in snapshot.children)
                        {
                            val tempData = locationSnapshot.key
                            if (tempData != null) {
                                string_test.add(tempData.toString())
                            }
                            var iterator = 1
                            var locationName = ""
                            var addressName = ""
                            var maxAttendees = ""
                            var hours = ""
                            var days = ""
                            for (valueSnapshot in locationSnapshot.children) {
                                // Accesezi fiecare valoare și copil din nodul locationSnapshot
                                val value = valueSnapshot.value.toString()

                                if(iterator == 1)
                                {
                                    locationName = value
                                }
                                else if(iterator == 2)
                                {
                                    addressName = value
                                }
                                else if(iterator == 3)
                                {
                                    maxAttendees = value
                                }
                                else if(iterator == 4)
                                {
                                    hours = value
                                }
                                else if(iterator == 5)
                                {
                                    days = value
                                }
                                iterator++
                            }
                            var locationData = LocationData(locationName,
                                                addressName,
                                                maxAttendees,
                                                hours,
                                                days)
                            nodesInDatabase.add(locationData)
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })



                StartOrderScreen(
                    onLocationsButtonClicked = {
                        navController.navigate(ToDoAppScreen.Entree.name)
                    },
                    onToDoButtonClicked = {
                        navController.navigate(ToDoAppScreen.ToDoView.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            composable(route = ToDoAppScreen.Entree.name) {




                ViewLocationsScreen(
                    locations = nodesInDatabase,

                    onNextButtonClicked = {
                        navController.navigate(ToDoAppScreen.SideDish.name)
                    },

                    string_test = string_test,

                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding),
                    onDetailsButtonClicked = {



                    },
                    onDeleteButtonClicked = {

                    }

                )
            }

            composable(route = ToDoAppScreen.Details.name){
                DetailsMenuScreen(
                    locations = nodesInDatabase,
                string_test = string_test,
                modifier = Modifier,
                )
            }


            composable(route = ToDoAppScreen.SideDish.name) {
                AddLocationMenuScreen(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding),
                    onSubmitButtonClicked = {
                        val auth:FirebaseAuth
                        val databaseRef:DatabaseReference


                        val locationName = getLocationName().trim()
                        val addressName = getAddressName().trim()
                        val maxAttendees = getMaxAttendees().trim()
                        val hours = getHours().trim()
                        val days = getDays().trim()

                        auth = FirebaseAuth.getInstance()

                        databaseRef = FirebaseDatabase.getInstance().reference.
                        child("Locations").
                        child(auth.currentUser?.uid.toString()).
                        child(locationName)
                        if(locationName.isNotEmpty() &&
                            addressName.isNotEmpty() &&
                            maxAttendees.isNotEmpty()&&
                            hours.isNotEmpty()       &&
                            days.isNotEmpty())
                        {
                            databaseRef.push().setValue(locationName)
                            databaseRef.push().setValue(addressName)
                            databaseRef.push().setValue(maxAttendees)
                            databaseRef.push().setValue(hours)
                            databaseRef.push().setValue(days).addOnCompleteListener({
                                navController.navigate(ToDoAppScreen.Entree.name)
                            })

                        }
                    }
                )

            }
        }
    }
}
