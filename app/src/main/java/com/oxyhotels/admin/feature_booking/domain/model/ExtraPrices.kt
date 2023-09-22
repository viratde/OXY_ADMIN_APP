package com.oxyhotels.admin.feature_booking.domain.model

data class ExtraPrices(
    val cash:Int,
    val ota:Int,
    val bank:Int,
    val amount:Int,
    val pending:Int,
    val chargeType:String,
    val extraNote:String,
    val accountingDates:List<String>
)