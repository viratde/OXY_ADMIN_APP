package com.oxyhotels.admin.feature_analytics.presentation.states

data class AnalyticsState(
    val isError: Boolean = false,
    val errorMessage: String = "",
    val analytics: Map<String,Map<String,Int> >,
    val isLoading:Boolean = false,
    val startDate:String,
    val endDate:String
    )
