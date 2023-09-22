package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.R

@Composable
fun CheckInCheckOutView(
    checkInTime:String,
    checkOutTime:String
){


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Text(
                    text = "Check In", style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 17.sp, color = MaterialTheme.colorScheme.secondary
                    )
                )

                Text(
                    text = checkInTime,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(130.dp)
                        .padding(top = 15.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onBackground)
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                )

            }


            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = "Check Out", style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 17.sp, color = MaterialTheme.colorScheme.secondary
                    )
                )

                Text(
                    text = checkOutTime,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 15.sp, color = MaterialTheme.colorScheme.secondary
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(130.dp)
                        .padding(top = 15.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.onBackground)
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                )

            }

        }
    }

}