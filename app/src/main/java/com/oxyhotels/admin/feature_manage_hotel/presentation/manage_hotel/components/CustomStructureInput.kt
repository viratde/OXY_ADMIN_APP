package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_manage_hotel.domain.util.ActualRoom
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.RoomType

@Composable
fun CustomStructureInput(
    floorIndex: Int,
    actualRooms: List<ActualRoom>,
    roomTypes: List<RoomType>,
    onFloorDelete: () -> Unit,
    onRoomChange: (Int, String, String) -> Unit,
    onRoomDelete: (Int) -> Unit,
    isEnabled: Boolean,
    onAddRoom: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Floor No ${floorIndex + 1}",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 13.sp
                ),
            )

            if (isEnabled) {
                IconButton(onClick = { onFloorDelete() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Floor",
                        tint = Color.White
                    )
                }
            }
        }

        val noOfVerticalGrids =
            if (actualRooms.size % 2 == 0) actualRooms.size / 2 else (actualRooms.size + 1) / 2

        for (i in 0 until noOfVerticalGrids) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                CustomRoomInput(
                    actualRoom = actualRooms[2 * i],
                    roomTypes = roomTypes,
                    onRoomNameChange = {
                        onRoomChange(2 * i, it, actualRooms[2 * i].roomType)
                    },
                    onRoomTypeChange = {
                        onRoomChange(2 * i, actualRooms[2 * i].roomNo, it)
                    },
                    onRoomDelete = {
                        onRoomDelete(2 * i)
                    },
                    isEnabled = isEnabled
                )

                if (actualRooms.size > (2 * i) + 1) {
                    CustomRoomInput(
                        actualRoom = actualRooms[(2 * i) + 1],
                        roomTypes = roomTypes,
                        onRoomNameChange = {
                            onRoomChange((2 * i) + 1, it, actualRooms[(2 * i) + 1].roomType)
                        },
                        onRoomTypeChange = {
                            onRoomChange((2 * i) + 1, actualRooms[(2 * i) + 1].roomNo, it)
                        },
                        onRoomDelete = {
                            onRoomDelete((2 * i) + 1)
                        },
                        isEnabled = isEnabled
                    )
                }
            }
        }

        if (isEnabled) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                HotelButton(text = "Add Room", isLoading = false) {
                    onAddRoom()
                }

            }

        }

    }


}