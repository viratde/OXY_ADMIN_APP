package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.viewmodels.AddBasicDetailViewModel
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelButton
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.AddHotelHeader
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.HotelTextFieldInput
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.components.HotelTimePicker

@Composable
fun AddBasicDetailScreen(
    viewModel: AddBasicDetailViewModel,
    isEnabled:Boolean = true,
    onNext: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val scrollState = rememberLazyListState()

    val state = viewModel.state.collectAsState()

    Screen(
        padding = 0
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .padding(bottom = 30.dp),
            state = scrollState
        ) {


            item() {

                AddHotelHeader(text = "Enter Hotel Details")

                HotelTextFieldInput(
                    value = state.value.hotelName,
                    onValueChange = { viewModel.setHotelName(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Enter Hotel Name",
                    backgroundColor = MaterialTheme.colorScheme.onBackground,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Next
                        )
                    }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .border(1.dp, Color.White, RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp),
                    isEnabled = isEnabled
                )

                HotelTextFieldInput(
                    value = state.value.hotelId,
                    onValueChange = { viewModel.setHotelId(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Enter Hotel Id",
                    backgroundColor = MaterialTheme.colorScheme.onBackground,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
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

                HotelTextFieldInput(
                    value = state.value.phoneNo,
                    onValueChange = { viewModel.setPhoneNumber(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Enter Phone Number",
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
                    isEnabled = isEnabled
                )

                HotelTextFieldInput(
                    value = state.value.minPrice.let { if(it == 0){""}else{it.toString()} },
                    onValueChange = { viewModel.setMinimumPrice(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Minimum Price",
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
                    isEnabled = isEnabled
                )

                HotelTextFieldInput(
                    value = state.value.maxPrice.let { if(it == 0){""}else{it.toString()} },
                    onValueChange = { viewModel.setMaximumPrice(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Maximum Price",
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
                    isEnabled = isEnabled
                )
                HotelTextFieldInput(
                    value = state.value.latitude,
                    onValueChange = { viewModel.setLatitude(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Enter Latitude",
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
                    isEnabled = isEnabled
                )

                HotelTextFieldInput(
                    value = state.value.longitude,
                    onValueChange = { viewModel.setLongitude(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Enter Longitude",
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
                    isEnabled = isEnabled
                )

                HotelTextFieldInput(
                    value = state.value.locationUrl,
                    onValueChange = { viewModel.setLocationUrl(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Enter Location Url",
                    backgroundColor = MaterialTheme.colorScheme.onBackground,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Uri,
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


                HotelTextFieldInput(
                    value = state.value.hotelAddress,
                    onValueChange = { viewModel.setHotelAddress(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Enter Hotel Address",
                    backgroundColor = MaterialTheme.colorScheme.onBackground,
                    keyboardOptions = KeyboardOptions.Default.copy(
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


                HotelTextFieldInput(
                    value = state.value.hotelDescription,
                    onValueChange = { viewModel.setHotelDescription(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Enter Hotel Description",
                    backgroundColor = MaterialTheme.colorScheme.onBackground,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Uri,
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
                    time = state.value.checkInTime,
                    onTimeChange = { viewModel.updateCheckInTime(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "CheckIn Time",
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
                    preTimeText = "CheckIn",
                    isEnabled = isEnabled
                )

                HotelTimePicker(
                    time = state.value.checkOutTime,
                    onTimeChange = { viewModel.updateCheckOut(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "CheckOut Time",
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
                    preTimeText = "CheckOut",
                    isEnabled = isEnabled
                )

                HotelTextFieldInput(
                    value = state.value.refundPercentage,
                    onValueChange = { viewModel.cancellationPolicy(it) },
                    textStyle = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    placeHolderText = "Enter Refund Percentage",
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
                    isEnabled = isEnabled
                )

                AddHotelButton(text = "Next", isLoading = false) {
                    onNext()
                }


            }

            }
    }
}