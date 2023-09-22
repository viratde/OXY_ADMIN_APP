package com.oxyhotels.admin.feature_manage_hotel.presentation.pricing

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_auth.domain.use_cases.AuthTokenUseCases
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.util.Response
import com.oxyhotels.admin.feature_manage_hotel.presentation.pricing.utils.PricingRequest
import dagger.hilt.android.lifecycle.HiltViewModel
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
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class AddPricingViewModel @Inject constructor(
    private val authUseCases: AuthTokenUseCases,
) : ViewModel() {

    private val calendar: Calendar = Calendar.getInstance()

    private val month = if((calendar.get(Calendar.MONTH)+1).toString().length == 1) "0${(calendar.get(Calendar.MONTH)+1)}" else (calendar.get(Calendar.MONTH)+1).toString()
    private val day = if(calendar.get(Calendar.DAY_OF_MONTH).toString().length == 1) "0${calendar.get(Calendar.DAY_OF_MONTH)}" else calendar.get(Calendar.DAY_OF_MONTH)
    private val hour = if(calendar.get(Calendar.HOUR_OF_DAY).toString().length == 1) "0${calendar.get(Calendar.HOUR_OF_DAY)}" else calendar.get(Calendar.HOUR_OF_DAY)
    private val minute = if(calendar.get(Calendar.MINUTE).toString().length == 1) "0${calendar.get(Calendar.MINUTE)}" else calendar.get(Calendar.MINUTE)
    private val date =  "${calendar.get(Calendar.YEAR)}-$month-$day-$hour-$minute"

    private val _state = MutableStateFlow(
        AddPricingState(
        startDate = date,
        endDate = date
    )
    )

    val state = _state


    fun setStartDate(startDate:String){
        _state.value = state.value.copy(
            startDate = startDate
        )
    }

    fun setEndDate(endDate:String){
        _state.value = state.value.copy(
            endDate = endDate
        )
    }

    fun setPax1Price(price:Int){
        _state.value = state.value.copy(
            pax1Price = price
        )
    }

    fun setPax2Price(price:Int){
        _state.value = state.value.copy(
            pax2Price = price
        )
    }

    fun setPax3Price(price:Int){
        _state.value = state.value.copy(
            pax3Price = price
        )
    }

    fun setRoomType(roomType: String){
        _state.value = state.value.copy(
            roomType = roomType
        )
    }
    fun clearMessage(){
        _state.value = state.value.copy(
            isError = false,
            errorMessage = ""
        )
    }

    suspend fun setPricing(hotelId:String):Boolean{

        val token = authUseCases.getTokenUseCases()

        _state.value = state.value.copy(isSaving = true)

        if(state.value.startDate == state.value.endDate){
            _state.value = state.value.copy(isSaving = false, isError = true, errorMessage = "Start and End date cannot be same")
            return false
        }

        if(state.value.pax1Price == 0){
            _state.value = state.value.copy(isSaving = false, isError = true, errorMessage = "Please Enter Correct Pax1Price")
            return false
        }

        if(state.value.pax2Price == 0){
            _state.value = state.value.copy(isSaving = false, isError = true, errorMessage = "Please Enter Correct Pax2Price")
            return false
        }

        if(state.value.pax3Price == 0){
            _state.value = state.value.copy(isSaving = false, isError = true, errorMessage = "Please Enter Correct Pax3Price")
            return false
        }

        if(state.value.roomType == ""){
            _state.value = state.value.copy(isSaving = false, isError = true, errorMessage = "Please Choose Correct Room Type")
            return false
        }

        val client = HttpClient(Android){
            install(ContentNegotiation){
                json(contentType = ContentType("Text","Plain"))
            }
        }

        try{

            val data = PricingRequest(
                startDate = state.value.startDate,
                endDate = state.value.endDate,
                pax1Price = state.value.pax1Price,
                pax2Price = state.value.pax2Price,
                pax3Price = state.value.pax3Price,
                roomType = state.value.roomType
            )
            val response:String = client.post("${Constant.domain}/admin/setPrice"){
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                }
                parameter("hotelId", hotelId)
                setBody(Gson().toJson(data))
            }.body()

            val rData = Gson().fromJson(response, Response::class.java)

            if(!rData.status){
                _state.value = state.value.copy(isSaving = false, isError = true, errorMessage = rData.message)
                return false
            }
            _state.value = state.value.copy(isSaving = false, isError = true, errorMessage = "Pricing Updated Successfully")
            return true
        }catch(err:Error){
            println(err)
            _state.value = state.value.copy(isSaving = false, isError = true, errorMessage = "Please Try After Some Time")
            return false
        }
    }

}