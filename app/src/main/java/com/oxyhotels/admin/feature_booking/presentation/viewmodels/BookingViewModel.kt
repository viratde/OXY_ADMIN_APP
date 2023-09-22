package com.oxyhotels.admin.feature_booking.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.oxyhotels.admin.common.util.Response
import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.domain.use_cases.BookingUseCases
import com.oxyhotels.admin.feature_booking.presentation.states.BookingState
import com.oxyhotels.admin.feature_booking.presentation.states.CheckInRequest
import com.oxyhotels.admin.feature_booking.presentation.states.ExtraPaymentRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class BookingViewModel(
    val bookingModel: BookingModel,
    val token: String,
    val bookingUseCases: BookingUseCases
) : ViewModel() {

    init {
        println(bookingModel)
    }

    private val _state = MutableStateFlow(
        BookingState(
            bookingModel = bookingModel,
            totalPrice = bookingModel.totalPrice - (bookingModel.advancedPaymentData?.toIntOrNull()
                ?: 0),
            checkInRequest = CheckInRequest(),
            extraPaymentRequest = ExtraPaymentRequest(
                accountingDates = bookingModel.accountingDates
            )
        )
    )
    val state = _state


    fun updateFloorNo(floorNo: String) {
        _state.update {
            state.value.copy(
                checkInRequest = state.value.checkInRequest.copy(
                    floorNo = floorNo
                )
            )
        }
    }

    fun updateRoomNo(roomNo: String) {
        _state.update {
            state.value.copy(
                checkInRequest = state.value.checkInRequest.copy(
                    roomNo = roomNo
                )
            )
        }
    }

    fun updateRegisterNo(registerNo: String) {
        _state.update {
            state.value.copy(
                checkInRequest = state.value.checkInRequest.copy(
                    registerNo = registerNo
                )
            )
        }
    }

    fun updateCash(cash: String) {
        _state.update {
            state.value.copy(
                checkInRequest = state.value.checkInRequest.copy(
                    cash = cash
                )
            )
        }

    }

    fun updateBank(bank: String) {
        _state.update {
            state.value.copy(
                checkInRequest = state.value.checkInRequest.copy(
                    bank = bank
                )
            )
        }
    }


    fun updatePending(pending: String) {
        _state.update {
            state.value.copy(
                checkInRequest = state.value.checkInRequest.copy(
                    pending = pending
                )
            )
        }
    }

    fun updateOta(ota: String) {
        _state.update {
            state.value.copy(
                checkInRequest = state.value.checkInRequest.copy(
                    ota = ota
                )
            )
        }
    }

    fun startCheckIn() {
        _state.update {
            state.value.copy(
                isCheckingInActive = !state.value.isCheckingInActive
            )
        }
    }


    suspend fun checkInGuest() {
        val checkInRequest = state.value.checkInRequest
        _state.update {
            state.value.copy(
                isCheckingIn = true
            )
        }

        if (checkInRequest.floorNo.isEmpty() || checkInRequest.floorNo.toIntOrNull() == null || checkInRequest.floorNo.toInt() <= 0) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isCheckingIn = false,
                    errorMessage = "Please enter correct Floor No."
                )
            }
            return
        }

        if (checkInRequest.roomNo.isEmpty() || checkInRequest.roomNo.toIntOrNull() == null || checkInRequest.roomNo.toInt() <= 0) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isCheckingIn = false,
                    errorMessage = "Please enter correct Room No."
                )
            }
            return
        }

        if (checkInRequest.registerNo.isEmpty() || checkInRequest.registerNo.toIntOrNull() == null || checkInRequest.registerNo.toInt() <= 0) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isCheckingIn = false,
                    errorMessage = "Please enter correct Register No."
                )
            }
            return
        }

        if (checkInRequest.cash.toInt() + checkInRequest.bank.toInt() + checkInRequest.ota.toInt() + checkInRequest.pending.toInt() != state.value.totalPrice) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isCheckingIn = false,
                    errorMessage = "Price does not add up to booking"
                )
            }
            return
        }


        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }

        try {
            val httpResponse = client.post(Constant.checkInUpdateRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("hotelId", bookingModel.hotelId)
                    parameter("bookingId", bookingModel.bookingId)
                }
                setBody(Gson().toJson(checkInRequest))
            }

            if (httpResponse.status != HttpStatusCode.OK) {
                println(httpResponse.body<String>())
                _state.update {
                    state.value.copy(
                        isError = true,
                        isCheckingIn = false,
                        errorMessage = "Please try after some time."
                    )
                }
                return
            }
            val response = httpResponse.body<String>()
            val rData = Gson().fromJson(response, Response::class.java)

            if (!rData.status) {
                _state.update {
                    state.value.copy(
                        isError = true,
                        isCheckingIn = false,
                        errorMessage = rData.message
                    )
                }
                return
            }
            _state.update {
                state.value.copy(
                    isError = true,
                    isCheckingIn = false,
                    isCheckingInActive = false,
                    errorMessage = "Updated Successfully"
                )
            }
            val booking = Gson().fromJson(rData.data, BookingModel::class.java)
            bookingUseCases.updateBookingUseCase(booking = booking)
            return

        } catch (err: Exception) {
            err.printStackTrace()
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please try after some time."
                )
            }
        }
    }

    fun clearMessage() {
        _state.update {
            state.value.copy(
                isError = false,
                errorMessage = ""
            )
        }
    }

    fun updateExtraPaymentName(extraPaymentName: String) {
        _state.update {
            state.value.copy(
                extraPaymentRequest = state.value.extraPaymentRequest.copy(
                    extraPaymentName = extraPaymentName
                )
            )
        }
    }

    fun updateExtraCash(cash: String) {
        _state.update {
            state.value.copy(
                extraPaymentRequest = state.value.extraPaymentRequest.copy(
                    cash = cash
                )
            )
        }
    }

    fun updateExtraBank(bank: String) {
        _state.update {
            state.value.copy(
                extraPaymentRequest = state.value.extraPaymentRequest.copy(
                    bank = bank
                )
            )
        }
    }

    fun updateExtraOta(ota: String) {
        _state.update {
            state.value.copy(
                extraPaymentRequest = state.value.extraPaymentRequest.copy(
                    ota = ota
                )
            )
        }
    }

    fun updateExtraPending(pending: String) {
        _state.update {
            state.value.copy(
                extraPaymentRequest = state.value.extraPaymentRequest.copy(
                    pending = pending
                )
            )
        }
    }

    fun startExtraPriceUpdate() {
        _state.update {
            state.value.copy(
                isCheckingOutActive = !state.value.isCheckingOutActive
            )
        }
    }

    fun updateExtraNote(extraNote: String) {
        _state.update {
            state.value.copy(
                extraPaymentRequest = state.value.extraPaymentRequest.copy(
                    extraPaymentNote = extraNote
                )
            )
        }
    }

    fun updateAccountingDates(dates: List<String>) {
        _state.update {
            state.value.copy(
                extraPaymentRequest = state.value.extraPaymentRequest.copy(
                    accountingDates = dates
                )
            )
        }
    }

    suspend fun updateExtraPrice() {
        val extraPriceRequest = state.value.extraPaymentRequest

        if (extraPriceRequest.cash.toInt() + extraPriceRequest.bank.toInt() + extraPriceRequest.ota.toInt() + extraPriceRequest.pending.toInt() <= 0) {
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please enter correct prices"
                )
            }
            return
        }

        if (extraPriceRequest.extraPaymentNote.isEmpty()) {
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please enter correct note"
                )
            }
            return
        }

        if (extraPriceRequest.accountingDates.isEmpty()) {
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please enter correct dates"
                )
            }
            return
        }

        _state.update {
            state.value.copy(
                isCheckingOut = true
            )
        }
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }
        try {
            val response = client.post(Constant.extraPriceUpdate) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("hotelId", bookingModel.hotelId)
                    parameter("bookingId", bookingModel.bookingId)
                }
                setBody(Gson().toJson(extraPriceRequest))
            }.body<String>()
            val rData = Gson().fromJson(response, Response::class.java)

            if (!rData.status) {
                _state.update {
                    state.value.copy(
                        isError = true,
                        isCheckingOut = false,
                        errorMessage = rData.message
                    )
                }
                return
            }
            _state.update {
                state.value.copy(
                    isError = true,
                    isCheckingOut = false,
                    isCheckingOutActive = false,
                    errorMessage = "Updated Successfully"
                )
            }
            val booking = Gson().fromJson(rData.data, BookingModel::class.java)
            bookingUseCases.updateBookingUseCase(booking = booking)
            return

        } catch (err: Exception) {
            err.printStackTrace()
            _state.update {
                state.value.copy(
                    isCheckingOut = false,
                    isError = true,
                    errorMessage = "Please try after some time"
                )
            }
        }


    }


}