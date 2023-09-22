package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_auth.domain.use_cases.AuthTokenUseCases
import com.oxyhotels.admin.feature_manage_hotel.domain.model.HotelStorage
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.util.AddHotelData
import com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddHotelViewModel @Inject constructor(
    private val authUseCases: AuthTokenUseCases
) : ViewModel() {


    private val _state = MutableStateFlow(AddHotelState())
    val state = _state


    suspend fun saveHotel(addHotelData: AddHotelData, context: Context): HotelStorage? {


        val token = authUseCases.getTokenUseCases()

        _state.value = state.value.copy(
            isLoading = true
        )

        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("Text", "Plain"))
            }
        }

        val map = mutableMapOf<String, String>()

        val formData = MultiPartFormDataContent(
            formData {
                for (i in addHotelData.imageData) {
                    for (j in i.value) {
                        val file = getFileFromUri(j.toUri(), context = context)
                        if (file != null) {
                            map[j] = file.name
                            append("images", file.readBytes(), Headers.build {
                                append(HttpHeaders.ContentType, ContentType.Image.Any.toString())
                                append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                            })
                        }
                    }
                }
            }
        )



        try {
            val response: String = client.post(Constant.addHotelImagesRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                }
                setBody(formData)
            }.body()

            val rData = Gson().fromJson(response, Response::class.java)

            if (!rData.status) {
                _state.value = state.value.copy(
                    isError = true,
                    errorMessage = rData.message,
                    isLoading = false
                )
                return null
            }

            val typeToken = object : TypeToken<Map<String, String>>() {}.type
            val serverImageData = Gson().fromJson<Map<String, String>>(rData.data, typeToken)
            val newImageData: MutableMap<String, MutableList<String>> = mutableMapOf()

            for (i in addHotelData.imageData) {
                val values = mutableListOf<String>()
                for (j in i.value) {
                    values.add(serverImageData[map[j].toString()].toString())
                }
                newImageData[i.key] = values
            }


            val newHotelData = addHotelData.copy(imageData = newImageData)

            val aResponse = client.post(Constant.addHotelRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                }
                setBody(Gson().toJson(newHotelData))
            }.body<String>()

            val aData = Gson().fromJson(aResponse, Response::class.java)
            if (!aData.status) {
                _state.value = state.value.copy(
                    isError = true,
                    errorMessage = aData.message,
                    isLoading = false
                )
                return null
            }
            _state.value = state.value.copy(
                isLoading = false
            )
            return Gson().fromJson(aData.data, HotelStorage::class.java)

        } catch (err: Error) {
            _state.value = state.value.copy(
                errorMessage = "Please try after some time",
                isError = true,
                isLoading = false
            )
            return null
        }

    }


    suspend fun updateHotel(
        addHotelData: AddHotelData,
        context: Context,
        hotelId: String
    ): HotelStorage? {
        try {
            val token = authUseCases.getTokenUseCases()

            _state.value = state.value.copy(
                isLoading = true
            )

            val client = HttpClient(Android) {
                install(ContentNegotiation) {
                    json(contentType = ContentType("Text", "Plain"))
                }
            }

            val map = mutableMapOf<String, String>()
            val oMap = mutableMapOf<String, MutableList<String>>()

            var isFile = false;

            val formData = MultiPartFormDataContent(
                formData {
                    for (i in addHotelData.imageData) {
                        oMap[i.key] = mutableListOf()
                        for (j in i.value) {
                            if (j.startsWith(Constant.domain)) {
                                val newMutableList = oMap[i.key]?.toMutableList()
                                if (newMutableList != null) {
                                    newMutableList.add(j)
                                    oMap[i.key] = newMutableList
                                }
                            } else {
                                val file = getFileFromUri(j.toUri(), context = context)
                                if (file != null) {
                                    isFile = true
                                    map[j] = file.name
                                    append("images", file.readBytes(), Headers.build {
                                        append(
                                            HttpHeaders.ContentType,
                                            ContentType.Image.Any.toString()
                                        )
                                        append(
                                            HttpHeaders.ContentDisposition,
                                            "filename=${file.name}"
                                        )
                                    })
                                }
                            }
                        }
                    }
                }
            )

            var data: String? = null

            if (isFile) {
                val response: String = client.post(Constant.addHotelImagesRoute) {
                    headers {
                        append("Content-Type", "application/json")
                        append("Authorization", "Bearer $token")
                    }
                    setBody(formData)
                }.body()

                val rData = Gson().fromJson(response, Response::class.java)

                if (!rData.status) {
                    _state.value = state.value.copy(
                        isError = true,
                        errorMessage = rData.message,
                        isLoading = false
                    )
                    return null
                }
                data = rData.data
            }
            val typeToken = object : TypeToken<Map<String, String>>() {}.type
            val serverImageData =
                data?.let { Gson().fromJson<Map<String, String>>(data, typeToken) }
            val newImageData: MutableMap<String, MutableList<String>> = mutableMapOf()

            for (i in addHotelData.imageData) {
                val values = mutableListOf<String>()
                if (serverImageData != null) {
                    for (j in i.value) {
                        if (!j.startsWith(Constant.domain)) {
                            values.add(serverImageData[map[j].toString()].toString())
                        }
                    }
                }
                val oValues = oMap[i.key]
                if (oValues != null) {
                    for (j in oValues) {
                        values.add(j.replace(Constant.domain, ""))
                    }
                }
                newImageData[i.key] = values
            }
            val newHotelData = addHotelData.copy(imageData = newImageData)


            val aResponse = client.post(Constant.updateHotelRoute) {
                headers {
                    append("Content-Type", "application/json")
                    append("Authorization", "Bearer $token")
                }
                parameter("hotelId", hotelId)
                setBody(Gson().toJson(newHotelData))
            }.body<String>()

            println(aResponse)
            val aData = Gson().fromJson(aResponse, Response::class.java)
            if (!aData.status) {
                _state.value = state.value.copy(
                    isError = true,
                    errorMessage = aData.message,
                    isLoading = false
                )
                return null
            }

            _state.value = state.value.copy(
                isLoading = false
            )
            return Gson().fromJson(aData.data, HotelStorage::class.java)


        } catch (err: Error) {
            _state.value = state.value.copy(
                errorMessage = "Please try after some time",
                isError = true,
                isLoading = false
            )
            return null
        }
    }

    fun clearMessage() {
        _state.value = state.value.copy(isError = false, errorMessage = "")

    }

    private fun getFileFromUri(uri: Uri, context: Context): File? {
        val contentResolver: ContentResolver = context.contentResolver

        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameColumnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameColumnIndex != -1) {
                    val displayName = it.getString(displayNameColumnIndex)
                    val file = File(context.cacheDir, displayName)
                    file.createNewFile()

                    contentResolver.openInputStream(uri)?.use { input ->
                        FileOutputStream(file).use { output ->
                            val buffer = ByteArray(4 * 1024) // Adjust buffer size as needed
                            var bytesRead = input.read(buffer)
                            while (bytesRead != -1) {
                                output.write(buffer, 0, bytesRead)
                                bytesRead = input.read(buffer)
                            }
                            output.flush()
                        }
                    }

                    return file
                }
            }
        }

        return null
    }
}

