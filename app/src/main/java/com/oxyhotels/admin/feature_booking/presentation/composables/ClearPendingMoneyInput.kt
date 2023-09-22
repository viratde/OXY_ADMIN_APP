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
import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.presentation.states.PendingState
import com.oxyhotels.admin.feature_booking.presentation.viewmodels.PendingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ClearPendingMoneyInput(
    bookingModel: BookingModel,
    state : State<PendingState>,
    pendingViewModel: PendingViewModel
) {

    val life = LocalLifecycleOwner.current

    if (state.value.loadingBookingId == bookingModel.bookingId) {
        Column(
            modifier = Modifier
                .padding(start = 0.dp, end = 10.dp, top = 15.dp, bottom = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            DropNumberInput(
                placeholder = "Cash",
                value = state.value.clearPendingState.cash,
                onValueChange = {
                    pendingViewModel.updateClearCash(it)
                }
            )

            DropNumberInput(
                placeholder = "Bank",
                value = state.value.clearPendingState.bank,
                onValueChange = {
                    pendingViewModel.updateClearBank(it)
                }
            )

            if(bookingModel.referenceId != null ){
                DropNumberInput(
                    placeholder = "Ota",
                    value = state.value.clearPendingState.ota,
                    onValueChange = {
                        pendingViewModel.updateClearOta(it)
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            AuthButton(
                text = "Cancel",
                modifier = Modifier.weight(1f)
            ) {
                pendingViewModel.startClearing(null)
            }

            AuthButton(
                text = "Update",
                isLoading = state.value.isLoading,
                modifier = Modifier.weight(1f)
            ) {
                life.lifecycleScope.launch {
                    withContext(Dispatchers.IO){
                        pendingViewModel.clearPendingPayment(
                            bookingId = bookingModel.bookingId,
                            pendingMoney = bookingModel.pendingPayment
                        )
                    }
                }
            }

        }

    }
}