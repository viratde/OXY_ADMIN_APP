package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DropNumberInput(
    placeholder:String,
    value: String,
    onValueChange:(String) -> Unit
){
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp,MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 2.dp)
    ) {
        Row(modifier = Modifier.width(120.dp), horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text = placeholder,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                fontSize = 14.sp
            )
            Text(
                text = " - ",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                fontSize = 14.sp
            )
        }

        BasicTextField(
            value = value.ifEmpty { "0" },
            onValueChange = {
                            if(it.toLongOrNull() != null){
                                onValueChange(it.toLong().toString())
                            }else{
                                onValueChange("0")
                            }
            },
            singleLine = true,
            textStyle = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            ),
            cursorBrush = Brush.linearGradient(listOf(MaterialTheme.colorScheme.secondary,MaterialTheme.colorScheme.secondary)),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .padding(vertical = 2.dp)
                .height(50.dp),
            decorationBox = {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    it()
                }
            }
        )

    }
}