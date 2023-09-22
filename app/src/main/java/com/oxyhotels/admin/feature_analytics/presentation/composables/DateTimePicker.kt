package com.oxyhotels.admin.feature_analytics.presentation.composables

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DateTimePicker(
    selectedDate: String,
    placeHolder:String,
    onDateChange:(String) -> Unit
){
    val context = LocalContext.current

    val dates = selectedDate.split("-")

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val aDates = dates.toMutableList()
            aDates[0] = mDayOfMonth.toString().let { if(it.length == 2) it else "0$it"  }
            aDates[1] = (mMonth + 1).toString().let { if(it.length == 2) it else "0$it"  }
            aDates[2] = mYear.toString().let { if(it.length > 2) it else "0$it"  }
            onDateChange(aDates.joinToString("-"))
        }, dates[2].toInt(), dates[1].toInt()-1, dates[0].toInt()
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clickable(onClick = {
                datePicker.show()
            }),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = placeHolder,
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 20.sp
            )
        )

        Text(
            text = selectedDate,
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 20.sp
            )
        )
    }
}
