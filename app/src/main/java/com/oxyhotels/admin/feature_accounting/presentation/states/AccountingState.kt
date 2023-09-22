package com.oxyhotels.admin.feature_accounting.presentation.states



data class AccountingState(
    val isLoading:Boolean = false,
    val startDate:String,
    val endDate:String,
    val isError: Boolean = false,
    val errorMessage:String = ""
)