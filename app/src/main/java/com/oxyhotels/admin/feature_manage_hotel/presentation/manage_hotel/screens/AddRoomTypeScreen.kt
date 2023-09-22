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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddRoomTypeViewModel
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelButton
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelHeader
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.CustomRoomTypeInput

@Composable
fun AddRoomTypeScreen(
    viewModel: AddRoomTypeViewModel,
    isEnabled: Boolean = true,
    isLoading: Boolean,
    onNext: () -> Unit,
){

    val scrollState = rememberLazyListState()

    val state by viewModel.state.collectAsState()

    Screen(
        padding = 0
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .padding(bottom = 30.dp),
            state = scrollState
        ) {


            item() {
                AddHotelHeader(text = "Add Room Types")

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
                            text = "RoomTypes",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                      if(isEnabled){
                          IconButton(onClick = { viewModel.addNewRoomType() }) {
                              Icon(
                                  imageVector = Icons.Default.Add,
                                  contentDescription = "Add New RoomType",
                                  tint = Color.White
                              )
                          }
                      }
                    }
                    state.roomTypes.mapIndexed{index,it ->
                        CustomRoomTypeInput(
                            state = it,
                            categoryName = "Category${index+1}",
                            onDelete = { viewModel.removeRoomType(index) },
                            onStateChange = {
                                viewModel.updateRoomType(it,index)
                            },
                            isEnabled= isEnabled
                        )
                    }
                }

                AddHotelButton(text = "Next", isLoading = isLoading) {
                    onNext()
                }
            }
        }
    }

}