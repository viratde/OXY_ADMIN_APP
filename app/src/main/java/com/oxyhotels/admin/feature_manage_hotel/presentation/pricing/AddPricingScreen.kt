package com.oxyhotels.admin.feature_manage_hotel.presentation.pricing

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.oxyhotels.admin.feature_manage_hotel.presentation.AllHotelsRoute
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelHeader
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.HotelButton
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.HotelTextFieldInput
import com.oxyhotels.admin.feature_manage_hotel.presentation.pricing.composables.HotelDateInput
import com.oxyhotels.admin.feature_manage_hotel.presentation.pricing.composables.HotelSelectInput
import com.oxyhotels.admin.feature_manage_hotel.presentation.pricing.composables.HotelTimePicker
import kotlinx.coroutines.launch

@Composable
fun AddPricingScreen(
    viewModel: AddPricingViewModel,
    values: List<String>,
    hotelId: String,
    navController: NavHostController,
    isEnabled: Boolean = true
) {

    val state = viewModel.state.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val life = LocalLifecycleOwner.current

    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(key1 = state.value.isError) {
        if (state.value.isError) {
            showMessage(state.value.errorMessage)
            viewModel.clearMessage()
        }
    }

    val onSave = {
        life.lifecycleScope.launch {
            val value = viewModel.setPricing(hotelId)
            if (value) {
                navController.navigate(AllHotelsRoute.route) {
                    launchSingleTop = true
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        }
    }

    Screen(
        padding = 0,
        verticalArrangement = Arrangement.Top
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .padding(bottom = 30.dp),
        ) {


            AddHotelHeader(text = "Update Pricing")

            HotelDateInput(
                date = state.value.startDate,
                onDateChange = { viewModel.setStartDate(it) },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp,
                    color = Color.White
                ),
                placeHolderText = "Choose Start Date",
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                preTimeText = "Start Date",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp),
                isEnabled = isEnabled
            )

            HotelTimePicker(
                time = state.value.startDate,
                onTimeChange = { viewModel.setStartDate(it) },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp,
                    color = Color.White
                ),
                placeHolderText = "Start Time",
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp),
                preTimeText = "Start Time",
                isEnabled = isEnabled
            )

            HotelDateInput(
                date = state.value.endDate,
                onDateChange = { viewModel.setEndDate(it) },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp,
                    color = Color.White
                ),
                placeHolderText = "Choose End Date",
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                preTimeText = "End Date",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp),
                isEnabled = isEnabled
            )
            HotelTimePicker(
                time = state.value.endDate,
                onTimeChange = { viewModel.setEndDate(it) },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp,
                    color = Color.White
                ),
                placeHolderText = "End Time",
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp),
                preTimeText = "End Time",
                isEnabled = isEnabled
            )


            HotelTextFieldInput(
                value = if (state.value.pax1Price == 0) "" else state.value.pax1Price.toString(),
                onValueChange = {
                    if (it.toIntOrNull() != null) {
                        viewModel.setPax1Price(it.toInt())
                    } else if (it == "") {
                        viewModel.setPax1Price(0)
                    }
                },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp,
                    color = Color.White
                ),
                placeHolderText = "Please Enter Pax1Price",
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp),
                isEnabled = isEnabled
            )


            HotelTextFieldInput(
                value = if (state.value.pax2Price == 0) "" else state.value.pax2Price.toString(),
                onValueChange = {
                    if (it.toIntOrNull() != null) {
                        viewModel.setPax2Price(it.toInt())
                    } else if (it == "") {
                        viewModel.setPax2Price(0)
                    }
                },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp,
                    color = Color.White
                ),
                placeHolderText = "Please Enter Pax2Price",
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp),
                isEnabled = isEnabled
            )

            HotelTextFieldInput(
                value = if (state.value.pax3Price == 0) "" else state.value.pax3Price.toString(),
                onValueChange = {
                    if (it.toIntOrNull() != null) {
                        viewModel.setPax3Price(it.toInt())
                    } else if (it == "") {
                        viewModel.setPax3Price(0)
                    }
                },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp,
                    color = Color.White
                ),
                placeHolderText = "Please Enter Pax3Price",
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp),
                isEnabled = isEnabled
            )


            HotelSelectInput(
                value = state.value.roomType,
                values = values,
                onValueChange = { viewModel.setRoomType(it) },
                textStyle = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp,
                    color = Color.White
                ),
                placeHolderText = "Choose Room Type",
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp),
                isEnabled = isEnabled
            )

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                HotelButton(text = "Update", isLoading = state.value.isSaving) {
                    onSave()
                }
            }
        }
    }
}