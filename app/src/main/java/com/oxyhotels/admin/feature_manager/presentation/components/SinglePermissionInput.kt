package com.oxyhotels.admin.feature_manager.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oxyhotels.admin.feature_manager.domain.models.Permission

@Composable
fun SinglePermissionInput(
    permission: Permission,
    isEnabled: Boolean,
    modifier: Modifier,
    onUpdatePermission: (Permission) -> Unit
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {


        ManagerBooleanInput(
            value = permission.canCancelBooking,
            label = "Cancel Booking",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canCancelBooking = !permission.canCancelBooking
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canCheckInBooking,
            label = "Check In Booking",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canCheckInBooking = !permission.canCheckInBooking
                )
            )
        }



        ManagerBooleanInput(
            value = permission.canNoShowBooking,
            label = "No Show Booking",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canNoShowBooking = !permission.canNoShowBooking
                )
            )
        }



        ManagerBooleanInput(
            value = permission.canAssignRoom,
            label = "Assign Rooms To Booking",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canAssignRoom = !permission.canAssignRoom
                )
            )
        }




        ManagerBooleanInput(
            value = permission.canUpdatePayment,
            label = "Update Collection",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canUpdatePayment = !permission.canUpdatePayment
                )
            )
        }



        ManagerBooleanInput(
            value = permission.canShiftRoom,
            label = "Shift Rooms",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canShiftRoom = !permission.canShiftRoom
                )
            )
        }




        ManagerBooleanInput(
            value = permission.canAddExtraPrice,
            label = "Add Extra Price",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canAddExtraPrice = !permission.canAddExtraPrice
                )
            )
        }




        ManagerBooleanInput(
            value = permission.canCheckOutBooking,
            label = "Check Out Booking",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canCheckOutBooking = !permission.canCheckOutBooking
                )
            )
        }



        ManagerBooleanInput(
            value = permission.canDeleteCollection,
            label = "Delete Collection",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canDeleteCollection = !permission.canDeleteCollection
                )
            )
        }



        ManagerBooleanInput(
            value = permission.canCreateBooking,
            label = "Create Booking",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canCreateBooking = !permission.canCreateBooking
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canViewAnalytics,
            label = "Analytics",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canViewAnalytics = !permission.canViewAnalytics
                )
            )
        }


        ManagerBooleanInput(
            value = permission.canViewBooking,
            label = "Bookings",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canViewBooking = !permission.canViewBooking
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canViewBookingDetail,
            label = "Booking Details",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canViewBookingDetail = !permission.canViewBookingDetail
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canViewPricing,
            label = "View Pricing",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canViewPricing = !permission.canViewPricing
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canUpdatePricing,
            label = "Update Pricing",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canUpdatePricing = !permission.canUpdatePricing
                )
            )
        }


        ManagerBooleanInput(
            value = permission.canModifyAmount,
            label = "Modify Amount",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canModifyAmount = !permission.canModifyAmount
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canModifyConvenienceFee,
            label = "Modify Convenience Fee",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canModifyConvenienceFee = !permission.canModifyConvenienceFee
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canDeleteExtraCharge,
            label = "Delete Extra Charge",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canDeleteExtraCharge = !permission.canDeleteExtraCharge
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canPutCustomDate,
            label = "Custom Date",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canPutCustomDate = !permission.canPutCustomDate
                )
            )
        }

        ManagerBooleanInput(
            value = permission.isTimeBounded,
            label = "Time Bounded",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    isTimeBounded = !permission.isTimeBounded
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canViewCode,
            label = "View Code",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canViewCode = !permission.canViewCode
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canAddExpense,
            label = "Add Expense",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canAddExpense = !permission.canAddExpense
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canApproveExpense,
            label = "Approve Expense",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canApproveExpense = !permission.canApproveExpense
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canAddPaymentToExpense,
            label = "Add Payment to Expense",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canAddPaymentToExpense = !permission.canAddPaymentToExpense
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canViewExpenses,
            label = "View Expenses",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canViewExpenses = !permission.canViewExpenses
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canRepayExpense,
            label = "Clear Advances",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canRepayExpense = !permission.canRepayExpense
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canAddExpenser,
            label = "Add Expenser",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canAddExpenser = !permission.canAddExpenser
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canViewExpenser,
            label = "View Expensers",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canViewExpenser = !permission.canViewExpenser
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canAddSources,
            label = "Add Source",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canAddSources = !permission.canAddSources
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canViewSources,
            label = "View Sources",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canViewSources = !permission.canViewSources
                )
            )
        }



        ManagerBooleanInput(
            value = permission.canDeleteTransaction,
            label = "Delete Transactions",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canDeleteTransaction = !permission.canDeleteTransaction
                )
            )
        }

        ManagerBooleanInput(
            value = permission.canViewUserAnalytics,
            label = "View User Analytics",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    canViewUserAnalytics = !permission.canViewUserAnalytics
                )
            )
        }

        ManagerBooleanInput(
            value = permission.isPropertyOwner,
            label = "Property Owner",
            isEnabled = isEnabled
        ) {
            onUpdatePermission(
                permission.copy(
                    isPropertyOwner = !permission.isPropertyOwner
                )
            )
        }



    }
}