package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AddHotelButton(text:String,isLoading:Boolean,onClick:() -> Unit){
    Button(
        modifier = Modifier
            .padding(top = 20.dp)
            .clip(RoundedCornerShape(14.dp))
            .fillMaxWidth()
            .animateContentSize(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.background
        ),
        onClick = { if(!isLoading) onClick() }
    ) {
        if(isLoading){
            CircularProgressIndicator(
                strokeWidth = 3.dp,
                color = Color.Black,
                modifier = Modifier.width(28.dp).padding(top=8.dp)
            )
        }else{
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.background
                ),
                fontSize=21.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}