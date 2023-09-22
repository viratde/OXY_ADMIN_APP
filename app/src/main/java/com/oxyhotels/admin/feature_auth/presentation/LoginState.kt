package com.oxyhotels.admin.feature_auth.presentation


data class LoginState(
    val phoneNumber:String = "",
    val password:String = "",
    val isLoading:Boolean = false,
    val isLoaded:Boolean = false,
    val isError: Boolean = false,
    val errorMessage:String = "",
    val authToken:String? = null,
)