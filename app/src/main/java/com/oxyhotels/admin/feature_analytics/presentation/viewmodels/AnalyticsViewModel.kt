package com.oxyhotels.admin.feature_analytics.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotels.admin.common.util.Response
import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_analytics.presentation.states.AnalyticsState
import com.oxyhotels.admin.feature_auth.domain.use_cases.AuthTokenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val authTokenUseCases: AuthTokenUseCases
) : ViewModel() {

    private val calendar : Calendar = Calendar.getInstance()
    private val aCalendar : Calendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_MONTH,1)
    }

    private val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    private val _state = MutableStateFlow(AnalyticsState(
        analytics = mapOf(),
        startDate = format.format(calendar.time),
        endDate = format.format(aCalendar.time)
    ))
    val state = _state

    fun updateStartDate(startDate:String){
        _state.update {
            state.value.copy(
                startDate = startDate
            )
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                loadAnalytics()
            }
        }
    }

    fun updateEndDate(endDate:String){
        _state.update {
            state.value.copy(
                endDate = endDate
            )
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                loadAnalytics()
            }
        }
    }

    suspend fun loadAnalytics (){

        _state.update {
            state.value.copy(
                isLoading = true
            )
        }
        val client = HttpClient(Android){
            install(ContentNegotiation){
                json(contentType = ContentType("Text","Plain"))
            }
        }

        try{

            val token = authTokenUseCases.getTokenUseCases()

            val jsonObject = JSONObject()
            jsonObject.put("startDate",state.value.startDate)
            jsonObject.put("endDate",state.value.endDate)
            val response = client.post(Constant.getAnalytics){
                headers {
                    append("Content-Type","application/json")
                    append("Authorization","Bearer $token")
                }
                setBody(jsonObject.toString())
            }.body<String>()

            val rData = Gson().fromJson(response,Response::class.java)

            println(rData.data)

            if(!rData.status || rData.data == null){
                _state.update {
                    state.value.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Please try after some time"
                    )
                }
                return
            }

            val typeToken = object :TypeToken <Map<String,Map<String,Int>> >(){}.type
            val analyticsData : Map<String,Map<String,Int> >  = Gson().fromJson(rData.data,typeToken)

            _state.update {
                state.value.copy(
                    isError = true,
                    isLoading = false,
                    errorMessage = "Successfully Updated.",
                    analytics = analyticsData
                )
            }

        }catch (err:Exception){
            err.printStackTrace()
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please try after some time",
                    isLoading = false
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
}