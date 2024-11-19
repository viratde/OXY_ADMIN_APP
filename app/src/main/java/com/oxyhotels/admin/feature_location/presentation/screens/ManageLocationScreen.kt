package com.oxyhotels.admin.feature_location.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_location.domain.models.LocationData
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelButton
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelHeader
import com.oxyhotels.admin.feature_manager.presentation.components.ManagersTextFieldInput

@Composable
fun ManageLocationScreen(
    isLoading: Boolean,
    onUpdateLocation: (LocationData) -> Unit,
    locationData: LocationData,
    isEnabled: Boolean,
    onAdd: () -> Unit
) {

    val focusRequester = LocalFocusManager.current


    Screen(
        padding = 0
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .padding(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                AddHotelHeader(text = "Manager Details")
            }

            item {
                ManagersTextFieldInput(
                    value = locationData.name,
                    label = "Name",
                    keyboardType = KeyboardType.Text,
                    onValueChange = {
                        onUpdateLocation(
                            locationData.copy(
                                name = it
                            )
                        )
                    },
                    isEnabled = isEnabled
                ) {
                    focusRequester.moveFocus(FocusDirection.Next)
                }
            }

            item {
                ManagersTextFieldInput(
                    value = locationData.latitude,
                    label = "Latitude",
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        onUpdateLocation(
                            locationData.copy(
                                latitude = it
                            )
                        )
                    },
                    isEnabled = isEnabled
                ) {
                    focusRequester.moveFocus(FocusDirection.Next)
                }
            }


            item {
                ManagersTextFieldInput(
                    value = locationData.longitude,
                    label = "Longitude",
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        onUpdateLocation(
                            locationData.copy(
                                longitude = it
                            )
                        )
                    },
                    isEnabled = isEnabled
                ) {
                    focusRequester.moveFocus(FocusDirection.Next)
                }
            }

            item {
                ManagersTextFieldInput(
                    value = locationData.distance,
                    label = "Distance",
                    keyboardType = KeyboardType.Number,
                    onValueChange = {
                        onUpdateLocation(
                            locationData.copy(
                                distance = it
                            )
                        )
                    },
                    isEnabled = isEnabled
                ) {
                    focusRequester.moveFocus(FocusDirection.Next)
                }
            }

            if (isEnabled) {
                item {
                    AddHotelButton(text = "Update", isLoading = isLoading) {
                        onAdd()
                    }
                }
            }

        }
    }

}