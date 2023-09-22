package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_manage_hotel.domain.util.ActualRoom
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states.RoomType

@Composable
fun CustomRoomInput(
    actualRoom: ActualRoom,
    roomTypes: List<RoomType>,
    onRoomNameChange: (String) -> Unit,
    isEnabled: Boolean,
    onRoomTypeChange: (String) -> Unit,
    onRoomDelete:() -> Unit
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(vertical = 5.dp, horizontal = 5.dp)
            .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = actualRoom.roomNo,
                onValueChange = {
                    onRoomNameChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.secondary,
                    unfocusedTextColor = MaterialTheme.colorScheme.secondary
                ),
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Column {
                Text(
                    text = actualRoom.roomType,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.clickable(onClick = { isExpanded = true })
                )
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier
                        .background(Color.Black)
                        .weight(1f)
                ) {
                    roomTypes.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it.type,
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.secondary
                                    ),
                                    color = Color.White
                                )
                            },
                            onClick = { onRoomTypeChange(it.type);isExpanded = false }
                        )
                    }
                }
            }

        }

        if (isEnabled) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable(onClick = {
                            onRoomDelete()
                        }),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colorScheme.background,
                        contentDescription = "image",
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp)
                    )
                }
            }
        }

    }
}