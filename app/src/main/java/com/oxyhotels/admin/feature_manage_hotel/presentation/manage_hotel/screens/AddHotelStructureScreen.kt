package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelButton
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelHeader
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.CustomStructureInput
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.AddRoomTypeState
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.RoomType
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddHotelStructureViewModel
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddRoomTypeViewModel

@Composable
fun AddHotelStructureScreen(
    viewModel: AddHotelStructureViewModel,
    isEnabled: Boolean = true,
    isLoading: Boolean,
    addRoomTypeViewModel: AddRoomTypeViewModel,
    onNext: (AddRoomTypeState) -> Unit,
) {

    val scrollState = rememberLazyListState()

    val addRoomTypeState = addRoomTypeViewModel.state.collectAsState()

    val state by viewModel.state.collectAsState()

    Screen(
        padding = 0
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight()
                .imePadding()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .padding(bottom = 30.dp),
            state = scrollState
        ) {


            item() {

                AddHotelHeader(text = "Add Rooms And Floors")

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "Floors",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        if (isEnabled) {
                            IconButton(onClick = { viewModel.addNewFloor() }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add New Floor",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    state.structure.mapIndexed { index, it ->
                        CustomStructureInput(
                            floorIndex = index,
                            actualRooms = it,
                            roomTypes = addRoomTypeState.value.roomTypes,
                            onFloorDelete = {
                                viewModel.removeFloor(index)
                            },
                            onRoomChange = { rIndex, name, roomType ->
                                viewModel.updateRoom(index, rIndex, name, roomType)
                            },
                            isEnabled = isEnabled,
                            onRoomDelete = {
                                viewModel.removeRoom(index, it)
                            }
                        ) {
                            viewModel.addRoom(index, roomType = addRoomTypeState.value.roomTypes[0].type)
                        }
                    }
                }

                AddHotelButton(text = "Next", isLoading = isLoading) {
                    onNext(addRoomTypeState.value)
                }

            }
        }
    }
}