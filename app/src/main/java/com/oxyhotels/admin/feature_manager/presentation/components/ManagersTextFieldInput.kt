package com.oxyhotels.admin.feature_manager.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.HotelTextFieldInput

@Composable
fun ManagersTextFieldInput(
    value: String,
    label: String,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    onMoveFocus: () -> Unit
) {

    HotelTextFieldInput(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 14.sp,
            color = Color.White
        ),
        placeHolderText = label,
        backgroundColor = MaterialTheme.colorScheme.onBackground,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            onMoveFocus()
        }),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .border(1.dp, Color.White, RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp),
        isEnabled = isEnabled
    )

}