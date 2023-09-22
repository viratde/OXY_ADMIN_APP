package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.height
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
fun HotelButton(text:String,isLoading:Boolean,onClick:() -> Unit){
    Button(
        modifier = Modifier
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(14.dp))
            .width(120.dp)
            .padding(vertical = 4.dp)
            .animateContentSize(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.background
        ),
        onClick = { if(!isLoading) onClick() },
        shape = RoundedCornerShape(15.dp)
    ) {
        if(isLoading){
            CircularProgressIndicator(
                strokeWidth = 3.dp,
                color = Color.White,
                modifier = Modifier.width(17.dp).height(21.dp).padding(vertical = 2.dp)
            )
        }else{
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize=13.sp,
                    color= MaterialTheme.colorScheme.background
                )
            )
        }
    }
}