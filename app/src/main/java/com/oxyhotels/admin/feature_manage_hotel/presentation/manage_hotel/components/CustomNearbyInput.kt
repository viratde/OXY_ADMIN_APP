package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.NearByData

@Composable
fun CustomNearByInput(
    state: NearByData,
    categoryName: String,
    onDelete: () -> Unit,
    onStateChange: (NearByData) -> Unit,
    isEnabled: Boolean
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineSmall.copy(

            ),

        )
        HotelTextFieldInput(
            value = state.type,
            onValueChange = {onStateChange(state.copy(type = it))},
            textStyle = MaterialTheme.typography.headlineSmall.copy(color = Color.White, fontSize = 13.sp),
            placeHolderText = "Enter Category Name",
            isEnabled = isEnabled,
            backgroundColor = MaterialTheme.colorScheme.onBackground
        )
        BulletPointInput(
            header = categoryName,
            bulletPoints = state.locations,
            onStateChange = {onStateChange(state.copy(locations = it))},
            modifier = Modifier
                .fillMaxWidth(),
            placeholderText = "Enter New Location for ${state.type}",
            isEnabled = isEnabled
        )

        if(isEnabled){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                HotelButton(text = "Remove", isLoading = false) {
                    onDelete()
                }
            }
        }

    }
}