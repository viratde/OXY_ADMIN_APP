package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun BulletPointInput(
    header:String,
    bulletPoints:MutableList<String>,
    onStateChange:( MutableList<String> ) -> Unit,
    modifier: Modifier=Modifier,
    placeholderText:String,
    isEnabled :Boolean
){




    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text =header,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp
                ),
            )
            if(isEnabled){
                IconButton(onClick = { onStateChange((bulletPoints + mutableListOf("")).toMutableList()) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add image category",
                        tint = Color.White
                    )
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            bulletPoints.mapIndexed {index,it ->
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "*",
                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 16.sp, color = Color.White),
                        modifier = Modifier.padding(end = 10.dp, top = 5.dp)
                    )

                    HotelTextFieldInput(
                        value = it,
                        onValueChange = {var newState=bulletPoints.toMutableList();newState[index]=it;onStateChange(newState)},
                        textStyle = MaterialTheme.typography.headlineSmall.copy(fontSize = 14.sp, color = Color.White),
                        placeHolderText = placeholderText,
                        backgroundColor = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxSize(0.7f),
                        isEnabled = isEnabled
                    )
                    if(isEnabled){
                        IconButton(
                            onClick = { val newState=bulletPoints.toMutableList();
                                newState.removeAt(index);onStateChange(newState)  },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "remove bullet Point",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

        }
    }
}