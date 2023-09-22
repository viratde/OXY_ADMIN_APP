package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PairText(
    propertyName:String,
    propertyValue:String,
    fontSize:Int = 14
){

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = propertyName,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = fontSize.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
        )

        Text(
            text = propertyValue,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = fontSize.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
        )
    }
}