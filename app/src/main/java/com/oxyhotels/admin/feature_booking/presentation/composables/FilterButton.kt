package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterButton(
    value:String,
    isSelected:Boolean=false,
    onClick:() -> Unit
){
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp)),
        colors = ButtonDefaults.buttonColors(
            contentColor = if(isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.secondary,
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground
        ),
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 12.sp,
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
        )
    }
}