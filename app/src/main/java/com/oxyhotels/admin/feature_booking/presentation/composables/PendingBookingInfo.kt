package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.presentation.states.PendingState
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.PendingViewModel

@Composable
fun PendingBookingInfo(
    bookingModel: BookingModel,
    pendingViewModel: PendingViewModel,
    state: State<PendingState>,
    onClick:() -> Unit = {}
){

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))
        .clickable(onClick = { onClick() })
        .background(MaterialTheme.colorScheme.onBackground)
        .padding(10.dp)
        .animateContentSize()
    ){

        Column(modifier = Modifier
            .fillMaxWidth()
        ) {

            Row(
                verticalAlignment = Alignment.Top,
            ) {
                Column() {

                    Column(modifier = Modifier
                        .padding(start = 0.dp, end = 10.dp, top = 0.dp)
                        .widthIn(150.dp, 160.dp)) {

                        Text(
                            text = "Name - ${bookingModel.name}",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 14.sp
                            ),
                        )
                        Text(
                            text = "Phone - ${bookingModel.phone}",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 12.sp
                            ),
                        )
                        Text(
                            text = "Id - OXY${bookingModel.bookingId}",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontSize = 12.sp
                            ),
                        )

                    }
                }

                Column(modifier = Modifier.padding(start = 15.dp,end=10.dp, bottom = 10.dp)) {

                    Text(
                        text = "Price - ₹${bookingModel.totalPrice}",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 12.sp
                        ),
                    )

                }

            }
            Text(
                text = "${bookingModel.checkInTime} - ${bookingModel.checkOutTime}",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 12.sp
                ),
            )
            Text(
                text = bookingModel.createdAt,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 12.sp
                ),
            )

           Text(
               text = "PendingMoney - ${bookingModel.pendingPayment}",
               style = MaterialTheme.typography.headlineSmall.copy(
                   fontSize = 12.sp
               ),
           )

            ClearPendingMoneyInput(
                bookingModel = bookingModel,
                state = state,
                pendingViewModel = pendingViewModel
            )

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 3.dp, top = 3.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top,
        ) {
            var text = "User"
            if( bookingModel.isCreatedByManager && !bookingModel.referenceId.isNullOrEmpty() ){
                text = "Oyo"
            }else if( bookingModel.isCreatedByManager && bookingModel.referenceId.isNullOrEmpty() ){
                text = "Oxy"
            }
            Text(
                text = text,
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 9.sp
                ),
            )

        }
    }

}