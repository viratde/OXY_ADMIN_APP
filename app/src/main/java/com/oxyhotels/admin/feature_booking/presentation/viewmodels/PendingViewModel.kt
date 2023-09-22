package com.oxyhotels.admin.feature_booking.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotels.admin.common.util.Response
import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.presentation.states.ClearPendingState
import com.oxyhotels.admin.feature_booking.presentation.states.PendingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PendingViewModel @AssistedInject constructor(
    @Assisted("hotelId") val hotelId: String,
    @Assisted("token") private val token: String,
) : ViewModel() {

    private val _state = MutableStateFlow(PendingState())
    val state = _state

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                loadRemoteData()
            }
        }
    }

    private suspend fun loadRemoteData() {
        if (state.value.isDataLoading) {
            return
        }

        _state.update {
            state.value.copy(
                isDataLoading = true
            )
        }

        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }

        try {

            val response = client.post(Constant.getPendingPaymentBookings) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("hotelId", hotelId)
                }
            }.body<String>()

            val rData = Gson().fromJson(response, Response::class.java)

            if (!rData.status || rData.data == null) {
                _state.update {
                    state.value.copy(
                        isDataLoading = false,
                        isError = true,
                        errorMessage = rData.message ?: "Please try after some time"
                    )
                }
                return
            }

            val typeToken = object : TypeToken<List<BookingModel>>() {}.type
            val bookings = Gson().fromJson<List<BookingModel>>(rData.data, typeToken)

            _state.update {
                state.value.copy(
                    isDataLoading = false,
                    bookings = bookings,
                    isError = true,
                    errorMessage = "Bookings Updated Successfully."
                )
            }

        } catch (err: Exception) {
            _state.update {
                state.value.copy(
                    isDataLoading = false,
                    isError = true,
                    errorMessage = err.message ?: "Please try after some time."
                )
            }
        }
    }

    fun startClearing(bookingId: Int?) {
        _state.update {
            state.value.copy(
                loadingBookingId = bookingId
            )
        }
    }

    suspend fun clearPendingPayment(pendingMoney:Int,bookingId: Int) {
        val clearRequest = state.value.clearPendingState
        val totalMoney = clearRequest.bank.toInt() + clearRequest.cash.toInt() + clearRequest.ota.toInt()
        if(totalMoney > pendingMoney || totalMoney <= 0){
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please Enter Correct Prices"
                )
            }
            return
        }
        _state.update {
            state.value.copy(
                isLoading = true
            )
        }
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }

        try {
            val response = client.post(Constant.clearPendingPaymentBookings) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("hotelId", hotelId)
                    parameter("bookingId",bookingId)
                }
                setBody(Gson().toJson(clearRequest))
            }.body<String>()

            val rData = Gson().fromJson(response, Response::class.java)

            println(rData)

            if (!rData.status) {
                _state.update {
                    state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = rData.message
                    )
                }
                return
            }

            _state.update {
                state.value.copy(
                    bookings = state.value.bookings.map {
                        if(it.bookingId == bookingId){
                            it.copy(
                                pendingPayment = it.pendingPayment - totalMoney,

                            )
                        }else{
                            it
                        }
                    },
                    isError = true,
                    errorMessage = "Cleared Successfully",
                    isLoading = false,
                    loadingBookingId = null,
                    clearPendingState = ClearPendingState()
                )
            }


        }catch (err:Exception){
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Please try after some time"
                )
            }
        }
    }

    fun updateClearOta(ota: String) {
        _state.update {
            state.value.copy(
                clearPendingState = state.value.clearPendingState.copy(
                    ota = ota
                )
            )
        }
    }

    fun updateClearBank(bank: String) {
        _state.update {
            state.value.copy(
                clearPendingState = state.value.clearPendingState.copy(
                    bank = bank
                )
            )
        }
    }

    fun clearMessage(){
        _state.update {
            state.value.copy(
                isError = false,
                errorMessage = ""
            )
        }
    }

    fun updateClearCash(cash: String) {
        _state.update {
            state.value.copy(
                clearPendingState = state.value.clearPendingState.copy(
                    cash = cash
                )
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("hotelId") hotelId: String,
            @Assisted("token") token: String
        ): PendingViewModel
    }

    companion object {
        fun providesFactory(
            assistedFactory: Factory,
            hotelId: String,
            token: String
        ): ViewModelProvider.Factory {

            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(hotelId, token) as T
                }
            }

        }
    }
}