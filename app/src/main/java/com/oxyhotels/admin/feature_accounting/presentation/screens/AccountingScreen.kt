package com.oxyhotels.admin.feature_accounting.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.oxyhotels.admin.common.composables.Screen
import com.oxyhotels.admin.feature_accounting.presentation.composables.AccountDatePicker
import com.oxyhotels.admin.feature_accounting.presentation.viewmodels.AccountingViewModel

@Composable
fun AccountingScreen(
    navController: NavController,
    accountingViewModel: AccountingViewModel
){

    val state = accountingViewModel.state.collectAsState()

    Screen(
        isScrollable = true,
        verticalArrangement = Arrangement.Top,
        padding = 0
    ) {

        AccountDatePicker(
            selectedDate = state.value.startDate,
            placeholder = "StartDate",
            onDateChange = {
                accountingViewModel.updateStartDate(it)
            }
        )

        AccountDatePicker(
            selectedDate = state.value.endDate,
            placeholder = "EndDate",
            onDateChange = {
                accountingViewModel.updateEndDate(it)
            }
        )

    }
}