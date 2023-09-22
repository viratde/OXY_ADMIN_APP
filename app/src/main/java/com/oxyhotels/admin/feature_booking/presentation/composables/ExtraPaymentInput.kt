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
fun ExtraPaymentInput(
    bookingViewModel: BookingViewModel,
    state: State<BookingState>
) {

    val life = LocalLifecycleOwner.current

    if (state.value.isCheckingOutActive) {
        Column(
            modifier = Modifier
                .padding(start = 0.dp, end = 10.dp, top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            DropSelectInput(
                value = state.value.extraPaymentRequest.extraPaymentName,
                placeholder = "Charge Type",
                options = listOf(
                    "Extra Charge",
                    "Extra Person Charge",
                    "Extra Meal Charge",
                    "Early Check In",
                    "Late Check Out",
                    "Other"
                ),
                onValueChange = {
                    bookingViewModel.updateExtraPaymentName(it)
                },
            )

            DropNumberInput(
                placeholder = "Cash",
                value = state.value.extraPaymentRequest.cash,
                onValueChange = {
                    bookingViewModel.updateExtraCash(it)
                }
            )

            DropNumberInput(
                placeholder = "Bank",
                value = state.value.extraPaymentRequest.bank,
                onValueChange = {
                    bookingViewModel.updateExtraBank(it)
                }
            )

            if(state.value.bookingModel.referenceId != null ){
                DropNumberInput(
                    placeholder = "Ota",
                    value = state.value.extraPaymentRequest.ota,
                    onValueChange = {
                        bookingViewModel.updateExtraOta(it)
                    }
                )
            }

            DropNumberInput(
                placeholder = "Pending",
                value = state.value.extraPaymentRequest.pending,
                onValueChange = {
                    bookingViewModel.updateExtraPending(it)
                }
            )


            DropStringTextInput(
                placeholder = "Note",
                value = state.value.extraPaymentRequest.extraPaymentNote,
                onValueChange = {
                    bookingViewModel.updateExtraNote(it)
                }
            )

            CheckBoxesInput(
                values = state.value.bookingModel.accountingDates,
                selectedValues = state.value.extraPaymentRequest.accountingDates,
                onValueChange = {
                    bookingViewModel.updateAccountingDates(it)
                })

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                AuthButton(
                    text = "Cancel",
                    modifier = Modifier.weight(1f)
                ) {
                    bookingViewModel.startExtraPriceUpdate()
                }

                AuthButton(
                    text = "Update",
                    isLoading = state.value.isCheckingOut,
                    modifier = Modifier.weight(1f)
                ) {
                    life.lifecycleScope.launch {
                        withContext(Dispatchers.IO){
                            bookingViewModel.updateExtraPrice()
                        }
                    }
                }

            }

        }

    }
}