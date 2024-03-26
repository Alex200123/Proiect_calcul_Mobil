
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

import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.ui.EntreeMenuScreen
import com.example.lunchtray.ui.OrderViewModel
import com.example.lunchtray.ui.AddLocationMenuScreen
import com.example.lunchtray.ui.AddToDoListScreen
import com.example.lunchtray.ui.SignInScreen
import com.example.lunchtray.ui.SignUpScreen
import com.example.lunchtray.ui.StartOrderScreen
import com.example.lunchtray.ui.ViewToDoListScreen
import com.example.lunchtray.ui.getEmail
import com.example.lunchtray.ui.getEmailSignin
import com.example.lunchtray.ui.getPassword
import com.example.lunchtray.ui.getPasswordSignin
import com.example.lunchtray.ui.getRepeatPassword
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


enum class ToDoAppScreen(@StringRes val title: Int) {
    SignIn(title = R.string.signin),
    SignUp(title = R.string.signup),
    Start(title = R.string.app_name),
    Entree(title = R.string.choose_entree),
    SideDish(title = R.string.choose_side_dish),
    Accompaniment(title = R.string.choose_accompaniment),
    Checkout(title = R.string.order_checkout),
    ToDoView(title = R.string.ToDoView),
    ToDoAdd(title = R.string.ToDoAdd)
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
fun ToDoApp() {
    //Create NavController
    val navController = rememberNavController()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = ToDoAppScreen.valueOf(
        backStackEntry?.destination?.route ?: ToDoAppScreen.Start.name
    )
    // Create ViewModel
    val viewModel: OrderViewModel = viewModel()

    Scaffold(
        topBar = {
            ToDoAppBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

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
                                auth.createUserWithEmailAndPassword(email,password)
                                navController.navigate(ToDoAppScreen.Start.name)
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
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            composable(route = ToDoAppScreen.Start.name) {
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
                EntreeMenuScreen(
                    options = DataSource.entreeMenuItems,
                    onCancelButtonClicked = {
                        viewModel.resetOrder()
                        navController.popBackStack(ToDoAppScreen.Start.name, inclusive = false)
                    },
                    onNextButtonClicked = {
                        navController.navigate(ToDoAppScreen.SideDish.name)
                    },

                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            composable(route = ToDoAppScreen.SideDish.name) {
                AddLocationMenuScreen(
                    options = DataSource.sideDishMenuItems,


                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding),

                )
            }
        }
    }
}
