package com.oxyhotels.admin.feature_auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.oxyhotels.admin.common.util.Response
import com.oxyhotels.admin.constant.Constant
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
   private val authTokenUseCases: AuthTokenUseCases
):ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state

    init {
        viewModelScope.launch {
            _state.update {
                state.value.copy(
                    authToken = authTokenUseCases.getTokenUseCases(),
                )
            }
        }
    }

    fun updatePhoneNumber(phone:String){
        _state.update {
            state.value.copy(
                phoneNumber = phone
            )
        }
    }

    fun updatePassword(password:String){
        _state.update {
            state.value.copy(
                password = password
            )
        }
    }

    suspend fun onSignIn(){

        _state.update {
            state.value.copy(isLoading = true)
        }

        if(state.value.phoneNumber.let{ it.isEmpty() || it.toDoubleOrNull() == null || it.length != 10 }){
            _state.update {
                state.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Please Enter Correct Phone Number"
                )
            }
            return
        }
        if(state.value.password.let { it.isEmpty() || it.length < 8 }){
            _state.update {
                state.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Please Enter Correct Password"
                )
            }
            return
        }

        val client = HttpClient(Android){
            install(ContentNegotiation){
                json(contentType = ContentType("Text","Plain"))
            }
        }

        val jsonObject = JSONObject()
        jsonObject.put("phone",state.value.phoneNumber)
        jsonObject.put("password",state.value.password)
        try{
            val response:String = client.post("${Constant.domain}${Constant.authRoute}"){
                headers {
                    append("Content-Type","application/json")
                }
                setBody(jsonObject.toString())
            }.body()

            val rData = Gson().fromJson(response, Response::class.java)
            println(rData)
            if(!rData.status || rData.data == null){
                _state.update {
                    state.value.copy(
                        isError = true,
                        errorMessage = rData.message,
                        isLoading = false
                    )
                }
                return
            }
            authTokenUseCases.setTokenUseCases(rData.data)

            _state.update {
                state.value.copy(
                    isLoading = false,
                    isLoaded = true,
                    isError = true,
                    authToken = rData.data,
                    errorMessage = "Logged In Successfully"
                )
            }
        }catch (err:Exception){
            err.printStackTrace()
            _state.update {
                state.value.copy(
                    isError = true,
                    errorMessage = "Please try after some time.",
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