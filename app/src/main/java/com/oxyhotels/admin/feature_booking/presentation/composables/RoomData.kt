package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_booking.domain.model.BookedRoom

@Composable
fun BookingRoomData(
    rooms: List<BookedRoom>,
    onUpdateRooms: (String, Int) -> Unit = { _, _ -> },
    onUpdateGuests: (String, Int, Int) -> Unit = { _, _, _ -> }
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Rooms and Guests", style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 14.sp, color = MaterialTheme.colorScheme.secondary
            ), modifier = Modifier.padding( vertical = 6.dp)
        )

        rooms.map {

            Column(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 3.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.secondary,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 20.dp),
            ) {

                CustomBookRoom(
                    mainText = it.roomType,
                    onClick = { action -> onUpdateRooms(it.roomType,action) },
                    value = it.noOfRooms.toString()
                )

                it.noOfGuests.mapIndexed { index, value ->
                    CustomBookRoom(
                        mainText = "Guests in Room${index + 1}",
                        onClick = { action -> onUpdateGuests(it.roomType,action,index) },
                        value = value.toString()
                    )
                }
            }

        }
    }
}