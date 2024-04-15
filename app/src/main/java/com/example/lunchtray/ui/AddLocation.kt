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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lunchtray.R


@Composable
fun AddLocationMenuScreen(
    modifier: Modifier = Modifier,
    onSubmitButtonClicked: () -> Unit,
) {
    BaseMenuScreenAddLocations(
        modifier = modifier,
        onSubmitButtonClicked = onSubmitButtonClicked
    )
}

//@Composable
//fun AddLocationMenuScreen(
//
//    modifier: Modifier = Modifier,
//    locationNameTextField: (String) -> Unit,
//) {
//    BaseMenuScreenAddLocations(
//
//
//        modifier = modifier,
//        locationNameTextField = locationNameTextField
//    )
//}


//@Preview
//@Composable
//fun AddLocationMenuPreview(){
//    AddLocationMenuScreen(
//        options = DataSource.sideDishMenuItems,
//
//        onCancelButtonClicked = {},
//
//        modifier = Modifier
//            .padding(dimensionResource(R.dimen.padding_medium))
//            .verticalScroll(rememberScrollState())
//    )
//}
