package com.oxyhotels.admin.feature_location.domain.use_cases

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oxyhotels.admin.constant.Constant
import com.oxyhotels.admin.feature_location.domain.models.Location
import com.oxyhotels.admin.feature_manager.domain.models.ManagerException
import com.oxyhotels.admin.feature_manager.domain.models.ManagerResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListAllLocationsUseCases(
    private val gson: Gson
) {
    @Throws(ManagerException::class)
    suspend operator fun invoke(
        token: String,
    ): ManagerResponse<List<Location>> {

        val httpClient = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("text", "plain"))
            }
        }
        try {
            return withContext(Dispatchers.IO) {
                val response = httpClient.post(Constant.getAllLocationsRoute) {
                    headers {
                        append("Authorization", "Bearer $token")
                        append("Content-Type", "application/json")
                    }
                }.body<String>()
                val typeToken = object : TypeToken<ManagerResponse<List<Location>>>() {}.type
                return@withContext gson.fromJson(response, typeToken)
            }
        } catch (err: Exception) {
            throw ManagerException(err.message ?: "Please try after some time.")
        }
    }
}