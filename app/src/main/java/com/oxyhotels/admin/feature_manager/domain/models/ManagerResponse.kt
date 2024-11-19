package com.oxyhotels.admin.feature_manager.domain.models


data class ManagerResponse<T>(
    val status: Boolean,
    val message: String,
    val data: T?
)
