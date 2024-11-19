package com.oxyhotels.admin.feature_manager.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelButton
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelHeader
import com.oxyhotels.admin.feature_manager.domain.models.Manager
import com.oxyhotels.admin.feature_manager.presentation.components.ManagerBasicDetailsInput
import com.oxyhotels.admin.feature_manager.presentation.components.ManagerPermissionsInput

@Composable
fun ManageManagerScreen(
    isLoading: Boolean,
    onUpdateManager: (Manager) -> Unit,
    manager: Manager,
    isEnabled: Boolean,
    hotels: List<HotelStorage>,
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

            ManagerBasicDetailsInput(
                manager = manager,
                isEnabled = isEnabled,
                onUpdateManager = onUpdateManager
            ) {
                focusRequester.moveFocus(FocusDirection.Next)
            }

            ManagerPermissionsInput(
                permissions = manager.permissions,
                isStructure = true,
                hotels = hotels,
                isEnabled = isEnabled,
                onUpdatePermissions = {
                    onUpdateManager(manager.copy(permissions = it))
                }
            )

            if(isEnabled){
                item {
                    AddHotelButton(text = "Update", isLoading = isLoading) {
                        onAdd()
                    }
                }
            }

        }

    }

}