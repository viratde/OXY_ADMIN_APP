package com.oxyhotels.admin.feature_manage_hotel.presentation.hotels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.domain.use_cases.HotelUseCases
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.util.Response
import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_auth.domain.use_cases.AuthTokenUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HotelsViewModel @Inject constructor(
    private val hotelUseCases: HotelUseCases,
    private val authUseCases: AuthTokenUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(HotelsState());

    val state: State<HotelsState> = _state

    init {
        loadLocalData()
        viewModelScope.launch {
                loadRemoteData()
        }
    }

    private fun loadLocalData() {
        hotelUseCases.getHotel.invoke().onEach {
            _state.value = state.value.copy(hotels = it)
        }.launchIn(viewModelScope)
    }

    private suspend fun loadRemoteData() {

        val token = authUseCases.getTokenUseCases()

        _state.value = state.value.copy(
            isRemoteHotelsLoading = true
        )

        try {

            val client = HttpClient(Android) {
                install(ContentNegotiation) {
                    json(contentType = ContentType("Text", "Plain"))
                }
            }

            val response: String = client.post(Constant.getAllHotelsRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                }
            }.body()

            val rData = Gson().fromJson(response, Response::class.java)

            if (!rData.status) {
                _state.value = state.value.copy(
                   isRemoteHotelsLoading = false
                )
            }
            val typeToken = object : TypeToken<MutableList<HotelStorage>>() {}.type
            val serverHotels = Gson().fromJson<MutableList<HotelStorage>>(rData.data, typeToken)

            withContext(Dispatchers.IO){
                hotelUseCases.clearHotels()
                if(serverHotels != null){
                    hotelUseCases.addHotels(serverHotels)
                }
            }

            _state.value = state.value.copy(
                isRemoteHotelsLoading = false,
                isRemoteHotelsLoaded = true
            )

        } catch (err: Error) {

        }
    }

    suspend fun addNewHotel(hotelStorage: HotelStorage) {
        withContext(Dispatchers.IO) {
            hotelUseCases.addHotel(hotelStorage)
        }
    }

}