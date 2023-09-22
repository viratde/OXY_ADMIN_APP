package com.oxyhotels.admin.feature_booking.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.oxyhotels.admin.common.util.Response
import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_booking.domain.model.BookedRoom
import com.oxyhotels.admin.feature_booking.domain.model.BookingModel
import com.oxyhotels.admin.feature_booking.domain.use_cases.BookingUseCases
import com.oxyhotels.admin.feature_booking.presentation.states.CreateBooking
import com.oxyhotels.admin.feature_booking.presentation.states.CreateBookingState
import com.oxyhotels.admin.feature_booking.presentation.utils.UserDataResponse
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.HotelUseCases
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar

class CreateBookingViewModel @AssistedInject constructor(
    private val bookingUseCases: BookingUseCases,
    private val hotelUseCases: HotelUseCases,
    @Assisted("token") private val token: String,
    @Assisted("hotelId") private val hotelId: String
) : ViewModel() {

    private val calendar: Calendar = Calendar.getInstance()
    private val aCalendar: Calendar = Calendar.getInstance().apply {
        add(Calendar.DATE, 1)
    }

    @SuppressLint("SimpleDateFormat")
    private val _state = MutableStateFlow(
        CreateBookingState(
            booking = CreateBooking(
                checkInTime = SimpleDateFormat("yyyy-MM-dd").format(calendar.time),
                checkOutTime = SimpleDateFormat("yyyy-MM-dd").format(aCalendar.time)
            )
        )
    )


    val state = _state

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val hotel = hotelUseCases.getHotelById(hotelId = hotelId)
                val rooms = mutableListOf<BookedRoom>()
                hotel?.roomTypes?.keys?.forEach {
                    val roomData = BookedRoom(
                        roomType = it,
                        noOfRooms = 1,
                        noOfGuests = mutableListOf(),
                    )
                    rooms.add(roomData)
                }
                println(rooms)
                _state.value = state.value.copy(
                    booking = state.value.booking.copy(
                        rooms = rooms
                    )
                )
            }
        }
    }


    fun updateName(name: String) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(name = name)
            )
        }
    }

    fun updatePhone(phone: String) {

        if (phone.toLongOrNull() != null && phone.length == 10) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getUserData(phone.toLong())
                }
            }
        }
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(phone = phone)
            )
        }
    }

    fun updateEmail(email: String) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(email = email)
            )
        }
    }

    fun updateCheckInTime(checkInTime: String) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(checkInTime = checkInTime)
            )
        }
    }

    fun updateCheckOutTime(checkOutTime: String) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(checkOutTime = checkOutTime)
            )
        }
    }

    fun updateReferenceId(referenceId: String) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(referenceId = referenceId)
            )
        }
    }

    fun updatePrice(price: String) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(price = price)
            )
        }
    }

    fun updateBookingSource(source: String) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(bookingSource = source)
            )
        }
    }


    fun updateIsConvenience(value: Boolean) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(isConvenience = value)
            )
        }
    }


    fun updateConvenienceData(value: String) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(convenienceData = value)
            )
        }
    }


    fun updateIsAdvancedPayment(value: Boolean) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(isAdvancedPayment = value)
            )
        }
    }

    fun updateAdvancedPaymentMethod(value: String) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(advancedPaymentMethod = value)
            )
        }
    }

    fun updateAdvancedPaymentData(value: String) {
        _state.update {
            state.value.copy(
                booking = state.value.booking.copy(advancedPaymentData = value)
            )
        }
    }

    fun updateRoomsValue(roomType: String, action: Int) {
        if (action < 0) {
            _state.update {
                state.value.copy(
                    booking = state.value.booking.copy(
                        rooms = state.value.booking.rooms.map {
                            if (it.roomType == roomType && it.noOfRooms > 0) {
                                val noOfGuests = it.noOfGuests.toMutableList()
                                noOfGuests.removeAt(noOfGuests.size - 1)
                                return@map it.copy(
                                    noOfRooms = it.noOfRooms - 1,
                                    noOfGuests = noOfGuests
                                )
                            }
                            it
                        }.toMutableList()
                    )
                )
            }
        } else if (action > 0) {
            _state.update {
                state.value.copy(
                    booking = state.value.booking.copy(
                        rooms = state.value.booking.rooms.map {
                            if (it.roomType == roomType) {
                                val noOfGuests = it.noOfGuests.toMutableList()
                                noOfGuests.add(0)
                                return@map it.copy(
                                    noOfRooms = it.noOfRooms + 1,
                                    noOfGuests = noOfGuests
                                )
                            }
                            it
                        }.toMutableList()
                    )
                )
            }
        }
    }

    fun updateGuest(roomType: String, action: Int, index: Int) {
        if (action < 0) {
            _state.update {
                state.value.copy(
                    booking = state.value.booking.copy(
                        rooms = state.value.booking.rooms.map {
                            if (it.roomType == roomType && it.noOfRooms > 0) {
                                val noOfGuests = it.noOfGuests.toMutableList()
                                if (noOfGuests[index] > 0) {
                                    noOfGuests[index] = noOfGuests[index] - 1
                                }
                                return@map it.copy(noOfGuests = noOfGuests)
                            }
                            it
                        }.toMutableList()
                    )
                )
            }
        } else if (action > 0) {
            _state.update {
                state.value.copy(
                    booking = state.value.booking.copy(
                        rooms = state.value.booking.rooms.map {
                            if (it.roomType == roomType) {
                                val noOfGuests = it.noOfGuests.toMutableList()
                                if (noOfGuests[index] < 3) {
                                    noOfGuests[index] = noOfGuests[index] + 1
                                }
                                return@map it.copy(noOfGuests = noOfGuests)
                            }
                            it
                        }.toMutableList()
                    )
                )
            }
        }
    }

    private suspend fun getUserData(phone: Long) {

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

            val jsonObject = JSONObject()
            jsonObject.put("phone", phone)
            val httpResponse = client.post(Constant.getUserDataRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                }
                setBody(jsonObject.toString())
            }
            if (httpResponse.status != HttpStatusCode(200, "ok")) {
                _state.update {
                    state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Please try after some time"
                    )
                }
                return
            }

            val response = httpResponse.body<String>()
            val rData = Gson().fromJson(response, Response::class.java)
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

            val aData = Gson().fromJson(rData.data, UserDataResponse::class.java)
            _state.update {
                state.value.copy(
                    isLoading = false,
                    booking = state.value.booking.copy(
                        name = aData.name,
                        email = aData.email
                    )
                )
            }
        } catch (err: Exception) {
            _state.update {
                state.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Please try after some time."
                )
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    suspend fun createBooking() {
        val booking = state.value.booking
        _state.update {
            state.value.copy(
                isLoading = true
            )
        }

        if (booking.phone.toLongOrNull() == null || booking.phone.length != 10) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Please enter correct phone."
                )
            }
            return
        }

        if (booking.name.isEmpty() || booking.name.length < 3) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Please enter correct name."
                )
            }
            return
        }

//        if (booking.email.isEmpty() || !booking.email.contains("@") || !booking.email.contains(".")) {
//            _state.update {
//                state.value.copy(
//                    isError = true,
//                    isLoading = false,
//                    errorMessage = "Please enter correct email."
//                )
//            }
//            return
//        }

        if (booking.price.isEmpty() || booking.price.toLongOrNull() == null) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Please enter correct price."
                )
            }
            return
        }
        if (booking.checkInTime.isEmpty() || booking.checkInTime.length != 10) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Please enter correct check in time."
                )
            }
            return
        }
        if (booking.checkOutTime.isEmpty() || booking.checkOutTime.length != 10) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Please enter correct check out time."
                )
            }
            return
        }
        val checkInDate = SimpleDateFormat("yyyy-MM-dd").parse(booking.checkInTime);
        val checkOutDate = SimpleDateFormat("yyyy-MM-dd").parse(booking.checkOutTime);
        if (checkOutDate == null || checkInDate == null || checkOutDate < checkInDate) {
            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Please enter correct check in time and check out time."
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
            val httpResponse = client.post(Constant.createBookingRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                    parameter("hotelId", hotelId)
                }
                setBody(Gson().toJson(booking))
            }

            if (httpResponse.status != HttpStatusCode.OK) {
                println(httpResponse.body<String>())
                _state.update {
                    state.value.copy(
                        isError = true,
                        isLoading = false,
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
                        isLoading = false,
                        errorMessage = rData.message
                    )
                }
                return
            }
            val booking = Gson().fromJson(rData.data, BookingModel::class.java)
            bookingUseCases.addBookingUseCases(listOf(booking))

            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Booking Created Successfully."
                )
            }
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



    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("hotelId") hotelId: String,
            @Assisted("token") token: String
        ): CreateBookingViewModel
    }

    companion object {
        fun providesFactory(
            assistedFactory: Factory,
            hotelId: String,
            token: String
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(hotelId = hotelId, token = token) as T
                }
            }
        }
    }

}