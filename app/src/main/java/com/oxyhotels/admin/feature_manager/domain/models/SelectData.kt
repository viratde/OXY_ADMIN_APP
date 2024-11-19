package com.oxyhotels.admin.feature_manager.domain.models

data class SelectData<T>(
    val title: String,
    val value: T
)