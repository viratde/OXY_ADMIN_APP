package com.oxyhotels.admin.feature_booking.presentation.states

data class ExtraPaymentRequest(
   val extraPaymentName:String = "Extra Charge",
   val cash:String = "0",
   val bank:String = "0",
   val ota:String = "0",
   val pending:String = "0",
   val extraPaymentNote:String = "",
   val accountingDates:List<String>
)