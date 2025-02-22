package com.oxyhotels.admin.feature_manager.domain.models

data class Permission(
    val canCancelBooking: Boolean = false,
    val canCheckInBooking: Boolean = false,
    val canNoShowBooking: Boolean = false,
    val canAssignRoom: Boolean = false,
    val canUpdatePayment: Boolean = false,
    val canShiftRoom: Boolean = false,
    val canAddExtraPrice: Boolean = false,
    val canCheckOutBooking: Boolean = false,
    val canDeleteCollection: Boolean = false,
    val canCreateBooking: Boolean = false,
    val canViewAnalytics: Boolean = false,
    val canViewBooking: Boolean = false,
    val canViewBookingDetail: Boolean = false,
    val canViewPricing: Boolean = false,
    val canUpdatePricing: Boolean = false,
    val canModifyAmount: Boolean = false,
    val canModifyConvenienceFee: Boolean = false,
    val canDeleteExtraCharge: Boolean = false,
    val canPutCustomDate: Boolean = false,
    val isTimeBounded: Boolean = false,
    val canViewCode: Boolean = false,
    val canAddExpense: Boolean = false,
    val canApproveExpense: Boolean = false,
    val canAddPaymentToExpense: Boolean = false,
    val canViewExpenses: Boolean = false,
    val canRepayExpense: Boolean = false,
    val canViewExpenser: Boolean = false,
    val canAddExpenser: Boolean = false,
    val canViewSources: Boolean = false,
    val canAddSources: Boolean = false,
    val canDeleteTransaction: Boolean = false,
    val canViewUserAnalytics: Boolean = false,
    val hotel: String = "",
    val isPropertyOwner: Boolean = false,
)