package com.oxyhotels.admin.feature_booking.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.oxyhotels.admin.feature_auth.presentation.composables.AuthButton
import com.oxyhotels.admin.feature_booking.presentation.states.BookingState
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.BookingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun updateInInput(
    state: State<BookingState>,
    bookingViewModel: BookingViewModel
){

    val life = LocalLifecycleOwner.current

    if(state.value.isCheckingInActive){
        Column(
            modifier = Modifier
                .padding(start = 0.dp, end = 10.dp, top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            DropNumberInput(
                placeholder = "Floor No",
                value = state.value.checkInRequest.floorNo,
                onValueChange = {
                    bookingViewModel.updateFloorNo(it)
                }
            )

            DropNumberInput(
                placeholder = "Room No",
                value = state.value.checkInRequest.roomNo,
                onValueChange = {
                    bookingViewModel.updateRoomNo(it)
                }
            )

            DropNumberInput(
                placeholder = "Register No",
                value = state.value.checkInRequest.registerNo,
                onValueChange = {
                    bookingViewModel.updateRegisterNo(it)
                }
            )

            DropNumberInput(
                placeholder = "Cash",
                value = state.value.checkInRequest.cash,
                onValueChange = {
                    bookingViewModel.updateCash(it)
                }
            )

            DropNumberInput(
                placeholder = "Bank",
                value = state.value.checkInRequest.bank,
                onValueChange = {
                    bookingViewModel.updateBank(it)
                }
            )

            if(state.value.bookingModel.referenceId != null ){
                DropNumberInput(
                    placeholder = "Ota",
                    value = state.value.checkInRequest.ota,
                    onValueChange = {
                        bookingViewModel.updateOta(it)
                    }
                )
            }

            DropNumberInput(
                placeholder = "Pending",
                value = state.value.checkInRequest.pending,
                onValueChange = {
                    bookingViewModel.updatePending(it)
                }
            )

            PairText(propertyName = "Total Price", propertyValue = state.value.totalPrice.toString())

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                AuthButton(
                    text = "Cancel",
                    modifier = Modifier.weight(1f)
                ) {
                    bookingViewModel.startCheckIn()
                }

                AuthButton(
                    text = "Update",
                    isLoading = state.value.isCheckingIn,
                    modifier = Modifier.weight(1f)
                ) {
                    life.lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            bookingViewModel.checkInGuest()
                        }
                    }
                }

            }

        }
    }
}