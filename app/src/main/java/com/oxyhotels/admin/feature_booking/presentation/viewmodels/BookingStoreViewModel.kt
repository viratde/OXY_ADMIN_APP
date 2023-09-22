package com.oxyhotels.admin.feature_booking.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotels.admin.common.util.Response
import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.domain.use_cases.BookingUseCases
import com.oxyhotels.admin.feature_booking.presentation.states.BookingStoreState
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
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.Calendar


class BookingStoreViewModel @AssistedInject constructor(
    val bookingUseCases: BookingUseCases,
    @Assisted("hotelId") val hotelId: String,
    @Assisted("token") private val token: String,
) : ViewModel() {


    val date = Calendar.getInstance()

    private val _state = MutableStateFlow(BookingStoreState(selectedDate = date.toString()))
    val state = _state

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                bookingUseCases.clearBookingUseCases()
                bookingUseCases.getBookingsUseCases().onEach { books ->
                    val inHouseBookings = books.filter {
                        it.hasCheckedIn && !it.hasCheckedOut
                    }
                    val upcomingBookings = books.filter {
                        !it.hasCheckedIn && !it.isCancelled && !it.hasNotShown
                    }
                    val completeBookings = books.filter {
                        it.hasCheckedOut || it.isCancelled || it.hasNotShown
                    }
                    _state.update {
                        state.value.copy(
                            inHouseBookings = inHouseBookings,
                            completeBookings = completeBookings,
                            upcomingBookings = upcomingBookings
                        )
                    }
                }.launchIn(viewModelScope)
                loadAllDataOnce()
            }
        }
    }

    private suspend fun loadAllDataOnce() {

        _state.update {
            state.value.copy(
                isUpcomingLoading = true,
                isInHouseLoading = true,
                isCompleteLoading = true
            )
        }

        try {
            val client = HttpClient(Android) {
                install(ContentNegotiation) {
                    json(contentType = ContentType("Text", "Plain"))
                }
            }

            val jsonObject = JSONObject()
            jsonObject.put("date", state.value.selectedDate)
            val httpResponse = client.post(Constant.allDataLoadedOnceRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("hotelId", hotelId)
                }
                setBody(jsonObject.toString())
            }

            if (httpResponse.status != HttpStatusCode(200, "ok")) {
                _state.update {
                    state.value.copy(
                        isInHouseLoading = false,
                        isCompleteLoading = false,
                        isUpcomingLoading = false,
                        isError = true,
                        errorMessage = "Please try after some time"
                    )
                }
                return
            }

            val response = httpResponse.body<String>()

            val rData = Gson().fromJson(response, Response::class.java)

            if (!rData.status || rData.data == null) {
                _state.update {
                    state.value.copy(
                        isInHouseLoading = false,
                        isCompleteLoading = false,
                        isUpcomingLoading = false,
                        isError = true,
                        errorMessage = rData.data ?: "Please try after some time"
                    )
                }
                return
            }
            val typeToken = object : TypeToken<List<BookingModel>>() {}.type
            val bookings = Gson().fromJson<List<BookingModel>>(rData.data, typeToken)

            bookingUseCases.addBookingUseCases(bookings = bookings)

            _state.update {
                state.value.copy(
                    isInHouseLoading = false,
                    isInHouseLoaded = true,
                    isUpcomingLoading = false,
                    isUpcomingLoaded = true,
                    isCompleteLoading = false,
                    isCompleteLoaded = true,
                    isError = true,
                    errorMessage = "Bookings Updated Successfully."
                )
            }
        }catch (err:Exception){

            _state.update {
                state.value.copy(
                    isInHouseLoading = false,
                    isInHouseLoaded = false,
                    isUpcomingLoading = false,
                    isUpcomingLoaded = false,
                    isCompleteLoading = false,
                    isCompleteLoaded = false,
                    isError = true,
                    errorMessage = err.message ?: "Please try after some time"
                )
            }

        }


    }

    private suspend fun loadInHouseBookings() {
        if (state.value.isInHouseLoading) {
            return
        }

        _state.update {
            state.value.copy(
                isInHouseLoading = true
            )
        }

        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }

        try {

            val httpResponse = client.post(Constant.inHouseRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("hotelId", hotelId)
                }
            }

            if (httpResponse.status != HttpStatusCode(200, "ok")) {
                _state.update {
                    state.value.copy(
                        isInHouseLoading = false,
                        isError = true,
                        errorMessage = "Please try after some time"
                    )
                }
                return
            }


            val response = httpResponse.body<String>()

            val rData = Gson().fromJson(response, Response::class.java)

            if (!rData.status || rData.data == null) {
                _state.update {
                    state.value.copy(
                        isInHouseLoading = false,
                        isError = true,
                        errorMessage = rData.data ?: "Please try after some time"
                    )
                }
                return
            }

            val typeToken = object : TypeToken<List<BookingModel>>() {}.type
            val bookings = Gson().fromJson<List<BookingModel>>(rData.data, typeToken)

            bookingUseCases.addBookingUseCases(bookings = bookings)

            _state.update {
                state.value.copy(
                    isInHouseLoading = false,
                    isInHouseLoaded = true,
                    isError = true,
                    errorMessage = "Bookings Updated Successfully."
                )
            }

        } catch (err: Exception) {
            _state.update {
                state.value.copy(
                    isInHouseLoading = false,
                    isError = true,
                    errorMessage = err.message ?: "Please try after some time."
                )
            }
        }
    }


    private suspend fun loadCompleteBookings() {
        if (state.value.isCompleteLoading) {
            return
        }

        _state.update {
            state.value.copy(
                isCompleteLoading = true
            )
        }

        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }

        try {

            val jsonObject = JSONObject()
            jsonObject.put("date", state.value.selectedDate)

            val httpResponse = client.post(Constant.completeHouseRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("hotelId", hotelId)
                }
                setBody(jsonObject.toString())
            }

            if (httpResponse.status != HttpStatusCode(200, "ok")) {
                _state.update {
                    state.value.copy(
                        isCompleteLoading = false,
                        isError = true,
                        errorMessage = "Please try after some time"
                    )
                }
                return
            }


            val response = httpResponse.body<String>()

            val rData = Gson().fromJson(response, Response::class.java)

            if (!rData.status || rData.data == null) {
                _state.update {
                    state.value.copy(
                        isCompleteLoading = false,
                        isError = true,
                        errorMessage = rData.data ?: "Please try after some time"
                    )
                }
                return
            }

            val typeToken = object : TypeToken<List<BookingModel>>() {}.type
            val bookings = Gson().fromJson<List<BookingModel>>(rData.data, typeToken)

            bookingUseCases.addBookingUseCases(bookings = bookings)

            _state.update {
                state.value.copy(
                    isCompleteLoading = false,
                    isCompleteLoaded = true,
                    isError = true,
                    errorMessage = "Bookings Updated Successfully."
                )
            }

        } catch (err: Exception) {
            err.printStackTrace()
            _state.update {
                state.value.copy(
                    isCompleteLoading = false,
                    isError = true,
                    errorMessage = err.message ?: "Please try after some time. "
                )
            }
        }
    }


    private suspend fun loadUpcomingBookings() {

        if (state.value.isUpcomingLoading) {
            return
        }


        _state.update {
            state.value.copy(
                isUpcomingLoading = true
            )
        }


        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }

        try {

            val httpResponse = client.post(Constant.upcomingHouseRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("hotelId", hotelId)
                }
            }

            if (httpResponse.status != HttpStatusCode(200, "ok")) {
                _state.update {
                    state.value.copy(
                        isUpcomingLoading = false,
                        isError = true,
                        errorMessage = "Please try after some time"
                    )
                }
                return
            }


            val response = httpResponse.body<String>()

            val rData = Gson().fromJson(response, Response::class.java)

            if (!rData.status || rData.data == null) {
                _state.update {
                    state.value.copy(
                        isUpcomingLoading = false,
                        isError = true,
                        errorMessage = rData.data ?: "Please try after some time"
                    )
                }
                return
            }

            val typeToken = object : TypeToken<List<BookingModel>>() {}.type
            val bookings = Gson().fromJson<List<BookingModel>>(rData.data, typeToken)

            bookingUseCases.addBookingUseCases(bookings = bookings)


            _state.update {
                state.value.copy(
                    isUpcomingLoading = false,
                    isUpcomingLoaded = true,
                    isError = true,
                    errorMessage = "Bookings Updated Successfully."
                )
            }

        } catch (err: Exception) {
            _state.update {
                state.value.copy(
                    isUpcomingLoading = false,
                    isError = true,
                    errorMessage = err.message ?: "Please try after some time."
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

    fun updateDate(date: String) {
        _state.update {
            state.value.copy(
                selectedDate = date
            )
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadCompleteBookings()
            }
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("hotelId") hotelId: String,
            @Assisted("token") token: String
        ): BookingStoreViewModel
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