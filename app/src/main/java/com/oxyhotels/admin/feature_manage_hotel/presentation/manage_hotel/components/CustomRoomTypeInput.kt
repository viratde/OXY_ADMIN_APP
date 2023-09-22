package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.RoomType


@Composable
fun CustomRoomTypeInput(
    state: RoomType,
    categoryName: String,
    onDelete: () -> Unit,
    onStateChange: (RoomType) -> Unit,
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
                fontSize = 13.sp,
                color = Color.White
            ),
        )
        HotelTextFieldInput(
            value = state.type,
            onValueChange = {onStateChange(state.copy(type = it))},
            textStyle = MaterialTheme.typography.headlineSmall.copy(color = Color.White, fontSize = 13.sp),
            placeHolderText = "Enter Room Type Name",
            backgroundColor = MaterialTheme.colorScheme.onBackground,
            isEnabled = isEnabled
        )
        HotelTextFieldInput(
            value = state.availableRooms.toString(),
            onValueChange = {if(it.toIntOrNull() != null){onStateChange(state.copy(availableRooms = it.toInt()))} },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            textStyle = MaterialTheme.typography.headlineSmall.copy(color = Color.White, fontSize = 13.sp),
            placeHolderText = "Enter available rooms for this type",
            backgroundColor = MaterialTheme.colorScheme.onBackground,
            isEnabled = isEnabled
        )
        BulletPointInput(
            header = "Enter Features",
            bulletPoints = state.features,
            onStateChange = {onStateChange(state.copy(features = it))},
            modifier = Modifier
                .fillMaxWidth(),
            placeholderText = "Enter New feature",
            isEnabled = isEnabled
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
           if(isEnabled){
               HotelButton(text = "Remove", isLoading = false) {
                   onDelete()
               }
           }
        }

    }

}