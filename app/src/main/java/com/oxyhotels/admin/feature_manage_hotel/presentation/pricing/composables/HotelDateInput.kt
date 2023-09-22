package com.oxyhotels.admin.feature_manage_hotel.presentation.pricing.composables

import android.app.DatePickerDialog
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
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.HotelTextFieldInput

@Composable
fun HotelDateInput(
    date:String,
    modifier: Modifier = Modifier,
    onDateChange:(String) -> Unit,
    textStyle: TextStyle,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeHolderText:String,
    backgroundColor: Color,
    preTimeText:String,
    isEnabled:Boolean
){

    val context= LocalContext.current

    val pDate = date.split("-")

    val datePickerDialog = DatePickerDialog(
        context,
        {_,mYear,mMonth,mDay ->
            val month = if((mMonth + 1).toString().length == 1) "0${(mMonth + 1)}" else (mMonth + 1).toString()
            val day = if(mDay.toString().length == 1) "0$mDay" else mDay
            onDateChange("$mYear-$month-$day-${pDate[3]}-${pDate[4]}")
        },
        pDate[0].toInt(),
        (pDate[1].toInt()-1),
        pDate[2].toInt()
    )

    Row(
        modifier = modifier
            .clickable(onClick = {if(isEnabled) {datePickerDialog.show()} })
    ) {
        Text(
            text = preTimeText,
            style = textStyle,
            modifier = Modifier.width(120.dp).padding(vertical = 14.dp)
        )
        HotelTextFieldInput(
            value = " -    $date",
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