package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun HotelTextFieldInput(
    value:String,
    onValueChange:(String) -> Unit,
    textStyle: TextStyle,
    keyboardOptions: KeyboardOptions=KeyboardOptions.Default,
    keyboardActions: KeyboardActions= KeyboardActions.Default,
    placeHolderText:String,
    backgroundColor:Color,
    modifier: Modifier=Modifier,
    isEnabled:Boolean
){
    BasicTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
                        },
        textStyle = textStyle,
        singleLine = false,
        enabled = isEnabled,
        decorationBox = {innerTextField ->  
            Row(
                modifier = modifier
                    .background(backgroundColor)
                    .padding(vertical = 14.dp, horizontal = 0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    if(value.isEmpty()){
                        Text(
                            text = placeHolderText,
                            style = textStyle
                        )
                    }
                    innerTextField()
                }
            }
        },
        cursorBrush = Brush.linearGradient(listOf(MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.secondary)),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )
}
