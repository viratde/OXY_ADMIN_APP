package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun HotelTimePicker(
    time:String,
    modifier: Modifier=Modifier,
    onTimeChange:(String) -> Unit,
    textStyle: TextStyle,
    keyboardOptions: KeyboardOptions=KeyboardOptions.Default,
    keyboardActions: KeyboardActions= KeyboardActions.Default,
    placeHolderText:String,
    backgroundColor:Color,
    preTimeText:String,
    isEnabled:Boolean
){
    var context= LocalContext.current

    val mTimePickerDialog = TimePickerDialog(
        context,
        {_, mHour : Int, mMinute: Int ->
            onTimeChange("${if(mHour.toString().length == 1) "0${mHour}" else mHour}-${if(mMinute.toString().length == 1) "0${mMinute}" else mMinute }")
        }, time.split("-")[0].toInt(),time.split("-")[1].toInt(), false
    )


    Row(
        modifier = modifier
            .clickable(onClick = {if(isEnabled) {mTimePickerDialog.show()} })
    ) {
        Text(
            text = preTimeText,
            style = textStyle,
            modifier = Modifier.width(120.dp).padding(vertical = 14.dp)
        )
        HotelTextFieldInput(
            value = " -    $time",
            onValueChange = {},
            textStyle = textStyle,
            placeHolderText = placeHolderText,
            backgroundColor = backgroundColor,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier,
            isEnabled = false
        )
    }
}